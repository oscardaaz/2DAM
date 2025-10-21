package DemoStringBuffer_Builder;

public class DemoStringBuffer_Builder {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer("Me encanta");
        for (int i = 0; i < 100000; i++) {

            sb.append(" Acceso  a Datos");
        }
        System.out.println("StringBuffer: " + (System.currentTimeMillis() - startTime) + " ms");


        startTime = System.currentTimeMillis();
        StringBuilder sBuilder = new StringBuilder("Me encanta");
        for (int i = 0; i < 100000; i++) {

            sBuilder.append(" Acceso  a Datos");
        }
        System.out.println("StringBuilder: " + (System.currentTimeMillis() - startTime) + " ms");


        startTime = System.currentTimeMillis();
        String cadena = "Me encanta";
        for (int i = 0; i < 100000; i++) {

            cadena += " Acceso  a Datos";
        }
        System.out.println("String: " + (System.currentTimeMillis() - startTime) + " ms");

    }


}
