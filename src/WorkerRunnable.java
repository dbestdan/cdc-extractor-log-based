import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class WorkerRunnable implements Runnable, Config {
	private long sessionEndTime = 0L;
	private Connection conn = null;
	private PreparedStatement prep = null;
	private Writer out = null;

	public WorkerRunnable(long sessionEndTime) {
		this.sessionEndTime = sessionEndTime;
		this.conn = Client.getConnection();
		String replicationSlot = "replication_slot_"+System.getProperty("replicationSlotNumber");
		String query = "SELECT data FROM pg_logical_slot_peek_changes('"+ replicationSlot +"', NULL, 1000)";
		try {
			prep = conn.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String fileName = "Changed_Data_Log_Based_" + dateFormat.format(new Date());
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), "UTF-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (System.currentTimeMillis() < sessionEndTime) {
			try {
				ResultSet rs = prep.executeQuery();
				writeLocalFile(rs);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void writeLocalFile(ResultSet rs) {
		try {
			while (rs.next()) {
				out.append(rs.getString(1)+"\n");
			}
			out.flush();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

	}
}
