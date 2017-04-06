import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WorkerRunnable implements Runnable {
	private long sessionEndTime = 0L;
	private Connection conn = null;
	private PreparedStatement prep = null;
	

	public WorkerRunnable(long sessionEndTime) {
		this.sessionEndTime = sessionEndTime;
		this.conn = Client.getConnection();
		String query = "SELECT data FROM pg_logical_slot_peek_changes('replication_slot', NULL, 1000)";
		try {
			prep = conn.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	@Override
	public void run() {
		while (System.currentTimeMillis() > sessionEndTime) {
			
		}

	}
}
