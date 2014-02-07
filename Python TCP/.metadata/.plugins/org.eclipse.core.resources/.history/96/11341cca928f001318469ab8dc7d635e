package ssh;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHv2 {
	public static void main(final String[] args) {
		try {
			final JSch jsch=new JSch();
			
			String host=System.getProperty("jacksonwilson") + "@192.168.2.2";
			if (args.length>0) {
				host=args[0];
			}
			final String user=host.substring(0, host.indexOf('@'));
			host=host.substring(host.indexOf('@')+1);
			final Session session=jsch.getSession(user, host, 22);
			
			// jsch.setKnownHosts("/home/anand/.ssh/known_hosts");
			// jsch.addIdentity("/home/anand/.ssh/id_rsa");
			// If two machines have SSH passwordless logins setup, the following line is not needed:
			session.setPassword("peace1");
			session.connect();
			final String command="ps -ef;date;hostname";
			// command=args[1];
			
			final Channel channel=session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);
			
			// channel.setInputStream(System.in);
			channel.setInputStream(null);
			
			((ChannelExec)channel).setErrStream(System.err);
			
			final InputStream in=channel.getInputStream();
			
			channel.connect();
			final byte[] tmp=new byte[1024];
			
			while (true) {
				while (in.available()>0) {
					final int i=in.read(tmp, 0, 1024);
					if(i<0)break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: "+channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				}catch(final Exception ee) {
					ee.printStackTrace();
				}
			}
			channel.disconnect();
			session.disconnect();
		}
		catch(final Exception e){
			System.out.println(e);
		}
	}       // end main
}       // end class