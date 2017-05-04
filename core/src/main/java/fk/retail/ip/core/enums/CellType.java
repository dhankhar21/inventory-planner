package fk.retail.ip.core.enums;

/**
 * Created by agarwal.vaibhav on 04/05/17.
 */
public class CellType {
    public static int getType(String columnName) {
        switch(columnName) {
            case "IPC Quantity Override":
                return 1;
            case "CDO Price Override":
                return 2;
            case "CDO Quantity Override":
                return 1;
            case "BizFin Quantity Recommendation":
                return 1;
            case "New SLA":
                return 1;
            default:
                return 3;

        }
    }
}
