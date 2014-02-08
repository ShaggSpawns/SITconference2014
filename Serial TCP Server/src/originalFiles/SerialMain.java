package originalFiles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
 
/*
 * Send RxTx communication to serial port
 */
public class SerialMain {
    public static BufferedReader input;
    public static OutputStream output;
    
    public static synchronized void writeData(final String data) {
        try {
            output.write(data.getBytes());
        } catch (final Exception e) {
            System.out.println("Insufficient connection with port.");
            System.out.println("Closing Program. Restart to Reconnect.");
            System.exit(0);
        }
    }
    
    public static void main(final String[] args) {
        
        try {
            final SerialClass obj = new SerialClass();
            int c=0;
            
            obj.initialize();
            
            input = SerialClass.input;
            output = SerialClass.output;
            
            //Input buffer
            final InputStreamReader Ir = new InputStreamReader(System.in);
            final BufferedReader Br = new BufferedReader(Ir);
            
            while (c!=9) {
                TimeUnit.MILLISECONDS.sleep(250); //To keep in order
                System.out.print("\n" + "Enter your choice :");
                c = Integer.parseInt(Br.readLine()); // Get input
                // Determine and send output
                switch(c) {
                case 0: // Stop
                    writeData("0");
                    TimeUnit.MILLISECONDS.sleep(250);
                    System.out.println("Stop.");
                     break;
                    
                case 1: // Forward Straight
                    writeData("0");
                    TimeUnit.MILLISECONDS.sleep(250); // Prevent serial overload
                    writeData("1");
                    break;
                    
                case 2: // Forward Right
                    writeData("0");
                    TimeUnit.MILLISECONDS.sleep(250); // Prevent serial overload
                    writeData("2");
                    break;
                    
                case 3: // Forward Left
                    writeData("0");
                    TimeUnit.MILLISECONDS.sleep(250); // Prevent serial overload
                    writeData("3");
                    break;

                case 6: // Help Menu
                    System.out.println(
                             "0: Stop\n"
                            + "1: Forward\n"
                            + "2: Forward + Right\n"
                            + "3: Forward + Left\n"
                            + "6: Help Menu\n"
                            + "9: Quit\n");
                    break;
                
                case 9: //Shut Down
                    System.out.println("Program shutting down.");
                    System.exit(0);
                }
            }
            
            
            final String inputLine = input.readLine(); // Get serial feedback
            System.out.println(inputLine); // display serial feedback
            
            obj.close();
            
        }
        catch(final Exception e){}
        
    }
}
