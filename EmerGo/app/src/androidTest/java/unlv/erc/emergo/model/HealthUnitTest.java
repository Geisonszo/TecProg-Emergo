package unlv.erc.emergo.model;

import junit.framework.TestCase;


public class HealthUnitTest extends TestCase{

    public void testGetNameHealthUnit(){
        HealthUnit healthUnit = new HealthUnit();
        String nameHospital = "Hospital Regional do Gama";
        healthUnit.setUnitType("Hospital Regional do Gama");
        assertEquals(nameHospital,healthUnit.getUnitType());
    }

    public void testSetHealthUnitEmpty(){
        HealthUnit healthUnit = new HealthUnit();
        healthUnit.setNameHospital("");
        assertEquals("", healthUnit.getNameHospital());
    }

    public void testSetHealthUnitNull(){
        HealthUnit healthUnit = new HealthUnit();
        healthUnit.setNameHospital("Hospital Regional do Gama");
        boolean result = true;
        if(healthUnit.getNameHospital() == null){
            assertFalse(result);
        }else{
            assertTrue(result);
        }
    }

    public void testGetUnitType(){
        HealthUnit healthUnit = new HealthUnit();
        String unitType = "Unidade Básica de saúde";
        healthUnit.setUnitType("Unidade Básica de saúde");
        assertEquals(unitType,healthUnit.getUnitType());
    }

    public void testSetUnitTypeEmpty(){
        HealthUnit healthUnit = new HealthUnit();
        healthUnit.setUnitType("");
        assertEquals("",healthUnit.getUnitType());
    }

    public void testSetUnitTypeNull(){
        HealthUnit healthUnit = new HealthUnit();
        healthUnit.setUnitType("Unidade Básica");
        boolean result = true;
        if(healthUnit.getUnitType() == null){
            assertFalse(result);
        }else{
            assertTrue(result);
        }
    }

    public void testGetAdress(){
        HealthUnit healthUnit = new HealthUnit();
        String adress = "QR 602 Conjunto 06 Casa 05";
        healthUnit.setAddress("QR 602 Conjunto 06 Casa 05");
        assertEquals(adress,healthUnit.getAddress());
    }

    public void testSetAdressEmpty(){
        HealthUnit healthUnit = new HealthUnit();
        healthUnit.setAddress("");
        assertEquals("",healthUnit.getAddress());
    }

    public void testSetAdressNull(){
        HealthUnit healthUnit = new HealthUnit();
        healthUnit.setAddress("QR 602 Conjunto 06 Casa 05");
        boolean result = true;
        if(healthUnit.getAddress() == null){
            assertFalse(result);
        }else{
            assertTrue(result);
        }
    }
}
