package HTTPRequest;

/**
 * Created by maxedman on 2017-04-20.
 */

public class JSonObjectConverter {

    public static void main(String[] args){
        Vessel testVessel = new Vessel();
        testVessel.setCallSign("call_sign");
        testVessel.setId("id_String");
        testVessel.setImo(12345678);
        testVessel.setMmsi(987654321);
        testVessel.setName("vessel_name");
        testVessel.setPhotoURL("www.oogle.com");
        testVessel.setStmVesselId(123123123);
        testVessel.setType("vessel_type");


    }
}
