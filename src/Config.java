import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Config {

	public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	public final static List<String> replication_slot_1 = new ArrayList<String>() {{
		add("bmsql_customer");
		add("bmsql_district");
		add("bmsql_history");
		add("bmsql_item");
		add("bmsql_new_order");
		add("bmsql_oorder");
		add("bmsql_stock");		
	}};
	public final static List<String> replication_slot_2 = new ArrayList<String>() {{
		add("bmsql_order_line");
	}};
	
}
