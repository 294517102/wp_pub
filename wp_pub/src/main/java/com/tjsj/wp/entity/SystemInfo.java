/**
 * 
 */
package com.tjsj.wp.entity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

import com.tjsj.m_util.spring.SpringUtil;

/**
 * @author andrew_silence
 * @date 2017年7月22日 下午12:04:36
 * @version V1.0   
 */
public class SystemInfo {
	
	
	
    /**系统名*/  
    private String os_name;  
    /**系统架构*/  
    private String os_arch ;  
    /**系统版本号*/  
    private String os_version ;  
    /**系统IP*/  
    private String os_ip ;  
    /**系统MAC地址*/  
    private String os_mac;  
    /**系统时间*/  
    private Date os_date;  
    /**系统CPU个数*/  
    private Integer os_cpus ;  
    /**系统用户名*/  
    private String os_user_name;  
    /**用户的当前工作目录*/  
    private String os_user_dir;  
    /**用户的主目录*/  
    private String os_user_home;  
      
    /**Java的运行环境版本*/  
    private String java_version ;  
    /**java默认的临时文件路径*/  
    private String java_io_tmpdir;  
      
    /**java 平台*/  
    private String sun_desktop ;  
      
    /**文件分隔符  在 unix 系统中是＂／＂*/  
    private String file_separator;  
    /**路径分隔符  在 unix 系统中是＂:＂*/  
    private String path_separator;  
    /**行分隔符   在 unix 系统中是＂/n＂*/  
    private String line_separator;  
      
    /**服务context**/  
    private String server_context ;  
    /**服务器名*/  
    private String server_name;  
    /**服务器端口*/  
    private Integer server_port;  
    /**服务器地址*/  
    private String server_addr;  
    /**获得客户端电脑的名字，若失败，则返回客户端电脑的ip地址*/  
    private String server_host;  
    /**服务协议*/  
    private String server_protocol; 
    
    private String databaseProductName;
    
    private String databaseProductVersion;
    
    private static String jdbc_driver;  
    private static String jdbc_url;  
    private static String jdbc_username;  
    private static String jdbc_password;  
    
      
    public static SystemInfo SYSTEMINFO;  
      
    public static SystemInfo getInstance(){  
        if(SYSTEMINFO == null){  
            SYSTEMINFO = new SystemInfo();  
        }  
        return SYSTEMINFO;  
    }  
      
      
    public static SystemInfo getInstance(HttpServletRequest request){  
        if(SYSTEMINFO == null){  
            SYSTEMINFO = new SystemInfo(request);  
        }  
        else {  
            SYSTEMINFO.ServerInfo(request);  
        }  
        return SYSTEMINFO;  
    }  
      
    public SystemInfo() {  
        super();  
        init();  
    }  
      
    public SystemInfo(HttpServletRequest request) {  
        super();  
        init();  
        /** 
         * 额外信息 
         */  
        ServerInfo(request);  
        getDatabaseMajorVersion();
    }  
    
    /** 
     * 输出信息 
     */  
    public void PrintInfo(){  
        Properties props=System.getProperties();  
        System.out.println("Java的运行环境版本："+props.getProperty("java.version"));   
        System.out.println("默认的临时文件路径："+props.getProperty("java.io.tmpdir"));   
        System.out.println("操作系统的名称："+props.getProperty("os.name"));   
        System.out.println("操作系统的构架："+props.getProperty("os.arch"));   
        System.out.println("操作系统的版本："+props.getProperty("os.version"));   
        System.out.println("文件分隔符："+props.getProperty("file.separator"));   //在 unix 系统中是＂／＂   
        System.out.println("路径分隔符："+props.getProperty("path.separator"));   //在 unix 系统中是＂:＂   
        System.out.println("行分隔符："+props.getProperty("line.separator"));   //在 unix 系统中是＂/n＂  
        System.out.println("用户的账户名称："+props.getProperty("user.name"));   
        System.out.println("用户的主目录："+props.getProperty("user.home"));   
        System.out.println("用户的当前工作目录："+props.getProperty("user.dir"));  
    }  
      
    /** 
     * 初始化基本属性 
     */  
    private void init(){  
        Properties props=System.getProperties();  
        this.java_version = props.getProperty("java.version");  
        this.java_io_tmpdir = props.getProperty("java.io.tmpdir");  
        this.os_name = props.getProperty("os.name");  
        this.os_arch = props.getProperty("os.arch");  
        this.os_version = props.getProperty("os.version");  
        this.file_separator = props.getProperty("file.separator");  
        this.path_separator = props.getProperty("path.separator");  
        this.line_separator = props.getProperty("line.separator");  
          
        this.os_user_name = props.getProperty("user.name");  
        this.os_user_home = props.getProperty("user.home");  
        this.os_user_dir = props.getProperty("user.dir");  
          
        this.sun_desktop = props.getProperty("sun.desktop");  
          
        this.os_date = new Date();  
        this.os_cpus = Runtime.getRuntime().availableProcessors();  
        
        this.os_cpus = Runtime.getRuntime().availableProcessors();  
        this.os_cpus = Runtime.getRuntime().availableProcessors();  
        
        try {  
            ipMac(); 
            getDatabaseMajorVersion();
        } catch (Exception e) {  
            this.os_ip = "";  
            this.os_mac = "";  
        }  
    }  
      
    /** 
     * 获取ip和mac地址 
     * @throws Exception 
     */  
    @SuppressWarnings("resource")  
    private void ipMac() throws Exception{  
        InetAddress address = InetAddress.getLocalHost();  
        NetworkInterface ni = NetworkInterface.getByInetAddress(address);  
        ni.getInetAddresses().nextElement().getAddress();  
        byte[] mac = ni.getHardwareAddress();  
        String sIP = address.getHostAddress();  
        String sMAC = "";  
        Formatter formatter = new Formatter();  
        for (int i = 0; i < mac.length; i++) {  
            sMAC = formatter.format(Locale.getDefault(), "%02X%s", mac[i],  
                    (i < mac.length - 1) ? "-" : "").toString();  
  
        }  
        SystemInfo.this.os_ip = sIP;  
        SystemInfo.this.os_mac = sMAC;  
    }  
      
    /** 
     * 获取服务器信息 
     * @param request 
     */  
    public void ServerInfo(HttpServletRequest request){  
        this.server_name = request.getServerName();  
        this.server_port = request.getServerPort();  
        this.server_addr = request.getRemoteAddr();  
        this.server_host = request.getRemoteHost();  
        this.server_protocol = request.getProtocol();  
        this.server_context = request.getContextPath();  
    }  
  
    public String getOs_name() {  
        return os_name;  
    }  
  
    public String getOs_arch() {  
        return os_arch;  
    }  
	public String getDatabaseProductName() {
		return databaseProductName;
	}
	public void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}
	public String getDatabaseProductVersion() {
		return databaseProductVersion;
	}
	public void setDatabaseProductVersion(String databaseProductVersion) {
		this.databaseProductVersion = databaseProductVersion;
	}


	public String gOss_version() {  
        return os_version;  
    }  
  
    public String getOs_ip() {  
        return os_ip;  
    }  
  
    public String getOs_mac() {  
        return os_mac;  
    }  
  
    public Date getOs_date() {  
        return os_date;  
    }  
  
    public Integer getOs_cpus() {  
        return os_cpus;  
    }  
  
    public String getOs_user_name() {  
        return os_user_name;  
    }  
  
    public String getOs_user_dir() {  
        return os_user_dir;  
    }  
  
    public String getOs_user_home() {  
        return os_user_home;  
    }  
  
    public String getJava_version() {  
        return java_version;  
    }  
  
    public String getJava_io_tmpdir() {  
        return java_io_tmpdir;  
    }  
  
    public String getSun_desktop() {  
        return sun_desktop;  
    }  
  
    public String getFile_separator() {  
        return file_separator;  
    }  
  
    public String getPath_separator() {  
        return path_separator;  
    }  
  
    public String getLine_separator() {  
        return line_separator;  
    }  
  
    public String getServer_context() {  
        return server_context;  
    }  
  
    public String getServer_name() {  
        return server_name;  
    }  
  
    public Integer getServer_port() {  
        return server_port;  
    }  
  
    public String getServer_addr() {  
        return server_addr;  
    }  
  
    public String getServer_host() {  
        return server_host;  
    }  
  
    public String getServer_protocol() {  
        return server_protocol;  
    }  
	
	@Override
	public String toString() {
		return "SystemInfo [os_name=" + os_name + ", os_arch=" + os_arch + ", os_version=" + os_version + ", os_ip="
				+ os_ip + ", os_mac=" + os_mac + ", os_date=" + os_date + ", os_cpus=" + os_cpus + ", os_user_name="
				+ os_user_name + ", os_user_dir=" + os_user_dir + ", os_user_home=" + os_user_home + ", java_version="
				+ java_version + ", java_io_tmpdir=" + java_io_tmpdir + ", sun_desktop=" + sun_desktop
				+ ", file_separator=" + file_separator + ", path_separator=" + path_separator + ", line_separator="
				+ line_separator + ", server_context=" + server_context + ", server_name=" + server_name
				+ ", server_port=" + server_port + ", server_addr=" + server_addr + ", server_host=" + server_host
				+ ", server_protocol=" + server_protocol + "]";
	}

	
	/** 
     * 通过Jdbc的方式获取数据库的版本 
     * @return 
     */  
    private void getDatabaseMajorVersion() {  
    	int version=0;
        try {  
     
        	DataSource ds= (DataSource) SpringUtil.getBean("dataSource");
            Connection con =  ds.getConnection();//DriverManager.getConnection(url, user, password);   
            // 获取数据库的信息  
            DatabaseMetaData dbMetaData = con.getMetaData();  
            // 返回一个String类对象，代表数据库的URL  
    /*        System.out.println("URL:" + dbMetaData.getURL() + ";");  
            // 返回连接当前数据库管理系统的用户名。  
            System.out.println("UserName:" + dbMetaData.getUserName() + ";");  
            // 返回一个boolean值，指示数据库是否只允许读操作。  
            System.out.println("isReadOnly:" + dbMetaData.isReadOnly() + ";");  
            // 返回数据库的产品名称。  
            System.out.println("DatabaseProductName:"  
                    + dbMetaData.getDatabaseProductName() + ";");  
            // 返回数据库的版本号。  
            System.out.println("DatabaseProductVersion:"  
                    + dbMetaData.getDatabaseProductVersion() + ";");  */
               
                try {  
                    Method gdbmvMethod = DatabaseMetaData.class.getMethod("getDatabaseMajorVersion", null);  
                    version = ( (Integer) gdbmvMethod.invoke(dbMetaData, null) ).intValue();  
                } catch (NoSuchMethodException nsme) {   
                }  
//                System.out.println("真正的版本号：" + version);  
              
            // 返回驱动驱动程序的名称。  
//            System.out.println("DriverName:" + dbMetaData.getDatabaseProductName() + ";");  
            // 返回驱动程序的版本号。  
//            System.out.println("DriverVersion:" + dbMetaData.getDatabaseProductVersion());  
            // 关闭连接 
            this.databaseProductName=dbMetaData.getDatabaseProductName();
            this.databaseProductVersion=dbMetaData.getDatabaseProductVersion();
            con.close();  
        } catch (Exception e) {  
            // 输出异常信息  
            System.err.println("SQLException :" + e.getMessage());  
            e.printStackTrace();  
        }  
//        return version;  
    }  

	public static void main(String[] args) {
		String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);
//		System.out.println(OS_NAME);
		//当前系统的位数X86?X64
		String OS_ARCH = System.getProperty("os.arch").toLowerCase(Locale.US);
//		System.out.println(OS_ARCH);
		//获取当前系统的版本信息
		String OS_VERSION = System.getProperty("os.version").toLowerCase(Locale.US);
//		System.out.println(OS_VERSION);
		
		SystemInfo info=new SystemInfo();
//		System.out.println("数据库名称"+info.getDatabaseProductName());
//		System.out.println("数据库版本"+info.getDatabaseProductVersion());
//		
//		System.out.println(info.toString());
	}

}
