package no_one;

// import this package for using the configure values or other such as mysql-username etc.
// if you want to use the value in this package, just like that: Configure.DRIVER  --(className.valueName)
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MysqlConnecter {
    /**
     * -------------
     * # if you want to connect mysql, you should go to com.teamghz.configure.MysqlConnecter.java to edit information
     * --------------
     * # insert/update -> int update(String sql) : "sql" is what you want to execute
     * # return a integer, when 0 -> false; when other(n) success and this operation affect n lines
     * --------------
     * # delete        -> int delete(String sql) : "sql" is what you want to execute
     * # return a integer, when 0 -> false; when other(n) success and this operation affect n lines
     * --------------
     * # query         -> ArrayList<Map<String, String>> select(String sql, String tableName) : 
     *                                      "sql" is what you want to execute
     *                                      "tableName" is the table name which you want to operate
     * # return a ArrayList, the elements in the ArrayList is Map<String, String>
     * # every Map is one query result
     * # when you need to use the data returned:
     * ArrayList<Map<String, String>> result = mc.select("select * from User", "User");
     *  for (Map<String, String> map : result) {
     *      System.out.println("______________________");
     *      for(Map.Entry<String, String> entry:map.entrySet()){    
     *            System.out.println(entry.getKey()+"--->"+entry.getValue());    
     *      }   
     *  }
     * --------------
     * 
     * 
	*--------------
	*�����������MySQL����Ӧ��ȥCOM.TeaGHZ.Conculy.mySqLConnect .java�༭��Ϣ��
	*--------------------
	*����/����-> int���£�String SQL������SQL��������Ҫִ�е�
	*����һ����������0 -> false����������n���ɹ��ʹ˲���Ӱ��n��ʱ
	*--------------------
	*>ɾ��-> intɾ�����ַ���SQL������SQL��������Ҫִ�е�
	*����һ����������0 -> false����������n���ɹ��ʹ˲���Ӱ��n��ʱ
	*--------------------
	*��ѯ-> ARARYLIST < MAP<�ַ�����String > >ѡ��String SQL��String TabLeNAME����
	*��SQL��������Ҫִ�е�
	*��TabLeNAMEY������������ı���
	*����һ�����飬�����е�Ԫ����map < string��string >
	*ÿһ����ͼ����һ����ѯ���
	*������Ҫʹ�÷��ص�����ʱ��
	* ARRAYList<MAP<String��String > >���= MC��ѡ�񣨡����û�ѡ��*�������û�������
	*��map < string��string > map�������{
	��ϵͳ��
	*��map .cc:string��String >��Ŀ��Map .CtrySyt������{
	*ϵͳ.out .pPRTLN����Ŀ.GETKEY����+��->��+��Ŀ.GETValueE������
	*}
	*}
	*--------------------
	*
	*/
    private Connection connection = null;
    private boolean connected = false;

    public MysqlConnecter() {
        try {
            Class.forName(Configure.DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR AT MysqlConnecter");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(Configure.URL, Configure.USERNAME, Configure.PASSWORD);
            connected = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//���뷽��
    public int insert(String sql)  {  
        int lineNum = 0;
        if (!connected) return 0;
        try{  
            PreparedStatement preStmt = connection.prepareStatement(sql);    
            lineNum = preStmt.executeUpdate();  
        }  
        catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return lineNum; 
    }
//���·���
    public int update(String sql){  
        int lineNum = 0;
        if (!connected) return 0;
        try{  
            PreparedStatement preStmt = connection.prepareStatement(sql);   
            lineNum = preStmt.executeUpdate();  
        }  
        catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return lineNum;
    }  
//���ҷ���
    public ArrayList<Map<String, String>> select(String sql, String tableName)
    {   
        ArrayList<Map<String, String>> result = new ArrayList<>();

        try  
        {  
            Statement stmt = connection.createStatement();  
            ResultSet rs = stmt.executeQuery(sql);
            int count=1;
            while (rs.next()){  	
                Map<String, String> tmp = new HashMap<>();
                tmp.put(String.valueOf(count), rs.getString(tableName));
                count++;
              result.add(tmp);
            }              
        }  
        catch (SQLException e){  
            e.printStackTrace();  
        }  
        return result;  
    }
 //ɾ������
    public int delete(String sql){   
        int lineNum = 0;    
        try  {  
            Statement stmt = connection.createStatement();  
            lineNum = stmt.executeUpdate(sql);  
        }  
        catch (SQLException e){  
            e.printStackTrace();  
        }  
        return lineNum;  
    }  
    // ��ȡ��ǰ��Ĺؼ��֣������ַ����������ʽ���أ��硰username������id����
    private String[] getFrame(String tableName) {
        String[] result = new String[Configure.TABLELEN];
         try  
            {  
                Statement stmt = connection.createStatement();  
                ResultSet rs = stmt.executeQuery("show columns from " + tableName);
                int i = 0;
                while (rs.next()){  
                    result[i++] = rs.getString(1);
                }
                result[i] = "#";
            }  
            catch (SQLException e){  
                e.printStackTrace();  
            }  
        return result;
    }
}