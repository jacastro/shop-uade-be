package ar.edu.uade.integracion.shop.service;

import ar.edu.uade.integracion.shop.entity.Address;
import ar.edu.uade.integracion.shop.entity.AddressDto;
import ar.edu.uade.integracion.shop.entity.Order;
import ar.edu.uade.integracion.shop.entity.ShippingOrder;
import ar.edu.uade.integracion.shop.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShippingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingService.class);
    private static final String CVS_SEPARATOR = ",";
    private final static String URL = "https://logistica-integracion.herokuapp.com/order/tienda/receive";
    private RestTemplate restTemplate;
    private OrderRepository orderRepository;
    private FtpClient ftpClient;

    public ShippingService(OrderRepository orderRepository) {
        restTemplate = new RestTemplate();
        this.orderRepository = orderRepository;
        ftpClient = new FtpClient("f24-preview.runhosting.com", 21, "3203234_clientes", "clientes123");
    }

    public void sendOrder(Order order) {
        if(order.getShippingId()==0) return;
        ResponseEntity<ShippingOrder> responseEntity = restTemplate.postForEntity(URL, map(order), ShippingOrder.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ShippingOrder response = responseEntity.getBody();
            order.setShippingStatus(response.getState());
            orderRepository.save(order);
        }
    }

    private ShippingOrder map(Order order){
        ShippingOrder shippingOrder = new ShippingOrder();
        shippingOrder.setAddress(map(order.getAddress()));
        shippingOrder.setBuyerId(order.getBuyer().getId());
        shippingOrder.setEmail(order.getBuyer().getEmail());
        shippingOrder.setItemId(order.getItem().getId());
        shippingOrder.setOrderId(order.getId());
        shippingOrder.setQuantity(order.getQuantity());
        shippingOrder.setWeight(order.getItem().getWeight());
        return shippingOrder;
    }

    private AddressDto map(Address address) {
        AddressDto dto = new AddressDto();
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setStreet(address.getStreet());
        dto.setZipCode(address.getZipCode());
        return dto;
    }

    public void getOrderStatus() {
        try {
            ftpClient.open();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ftpClient.getFile("/info.csv", out);
            updateOrders(getOrderStatusFromCsv(out.toByteArray()));
            ftpClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateOrders(Map<Integer, String> orderStatusFromCsv) {
        if(orderStatusFromCsv.isEmpty()) return;
        Iterable<Order> orders = orderRepository.findAllById(orderStatusFromCsv.keySet());
        orders.forEach(o -> o.setShippingStatus(orderStatusFromCsv.get(o.getId())));
        orderRepository.saveAll(orders);
    }

    private Map<Integer, String> getOrderStatusFromCsv(byte[] file) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Map<Integer, String> orderStatus = new HashMap<>();
        br.readLine(); //headers
        while ((line = br.readLine()) != null) {
            String str[] = line.split(CVS_SEPARATOR);
            orderStatus.put(Integer.valueOf(str[0]), str[1].replaceAll("^\"|\"$", ""));
        }
        return orderStatus;
    }
}
