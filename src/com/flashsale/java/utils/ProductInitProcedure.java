package com.flashsale.java.utils;

import java.sql.Connection;
import java.sql.Statement;

public class ProductInitProcedure {
    public static void initProcedure(){
        try(
                Connection conn = DatabaseConnectionManager.openConnection();
                Statement stmt = conn.createStatement();
        ){
            String FUNC_ProductCaculateRevenue = """
                    DELIMITER $$
                    
                    CREATE FUNCTION FUNC_CalculateCategoryRevenue(p_category VARCHAR(255))
                    RETURNS DECIMAL(15,2)
                    DETERMINISTIC
                    BEGIN
                        DECLARE total DECIMAL(15,2);
                    
                        SELECT SUM(od.quantity * od.unit_price)
                        INTO total
                        FROM Order_Details od
                        JOIN Products p ON od.product_id = p.id
                        WHERE p.category = p_category;
                    
                        RETURN IFNULL(total, 0);
                    END $$
                    
                    DELIMITER ;
                    """;


            stmt.execute(FUNC_ProductCaculateRevenue);
        }catch(Exception e){
            System.out.println("Error : " +e.getMessage());
        }
    }

    static void main(String[] args) {
        Connection con = DatabaseConnectionManager.openConnection();
        DatabaseConnectionManager.initDB();
    }
}
