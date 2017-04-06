import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		long runDuration = Long.parseLong(System.getProperty("runDuration"))*60000L;
		long sessionEndTime = System.currentTimeMillis()+runDuration;
		System.out.println("run Duration ="+ runDuration);
		
		
		
		//create Worker
		WorkerRunnable workerRunnable = new WorkerRunnable(sessionEndTime);
		threads.add(new Thread(workerRunnable));
		
		for(int i=0; i<threads.size(); i++){
			threads.get(i).start();
		}
		try {
			Thread.sleep(runDuration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.exit(0);

	}
	
	public static Connection getConnection(){
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", "benchmarksql");
		connectionProps.put("password", "benchmarksql");
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://pcnode2:5432/benchmarksql", connectionProps);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}