package practice.bootpay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BootPayOrderDto {
    private int status;
    private int code;
    private String message;
    private Data data;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Data{
        private int status;
        private String receipt_id;
        private String order_id;
        private String name;
        private int price;
    }
}
