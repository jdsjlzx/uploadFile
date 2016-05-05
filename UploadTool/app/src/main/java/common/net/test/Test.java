/**
 * Copyright ® 2016 DQ ENCH Co. Ltd.
 * All right reserved.
 */
package common.net.test;

import java.io.File;
import java.io.IOException;

import common.net.HttpRequest;
import common.net.HttpResponse;
import common.net.HttpUtils;
import common.net.Method;

public class Test {
 public static void main(String[] args) throws Exception {
  String ak="";
  String service_id=""; 
  String entity_name=""; //实体名称，实体是指一个人或一台车。
  String point_list="D:\\MyCsv.csv";//批量上传坐标文件路径
  String coord_type="1"; //1：GPS经纬度坐标2：国测局加密经纬度坐标 3：百度加密经纬度坐标
  String latitude=""; //用于单点上传的纬度
  String longitude=""; //用于单点上传的经度
  String loc_time="";  //用于单点上传坐标的轨迹点采集的GPS时间,类型是时间戳
  String return_type="0"; //0代表返回全部结果，1代表只返回entity_name字段。默认值为0	
  //----------------以下参数是用于查询历史轨迹点的参数--------------
  String start_time="";//查询历史轨迹点的开始时间 类型是时间戳
  String end_time="";   //查询历史轨迹点的结束时间 类型是时间戳
  String sort_type="0";//默认值是0：返回轨迹点按loc_time从大到小排序；当设为1时，则反之
  String simple_return="0";//默认值是0，当设为1时，仅返回轨迹点的经纬度
  String is_processed="0";//默认值是0。0为返回原始轨迹，1为返回纠偏轨迹
  
   //添加一个新的entity
   //addEntity(ak,service_id,entity_name);
   //批量上传坐标集合
   //addpoints(ak,service_id,entity_name,point_list);
   //单条上传坐标
   //addpoint(ak,service_id,entity_name,coord_type,latitude,longitude,loc_time);
   //查询所有的entity
   //listEntity(ak,service_id,return_type);
   //查询指定的entity
   //serviceEntity(ak,service_id,entity_name,return_type);
   //查询历史轨迹
    gethistory(ak,service_id,entity_name,start_time,end_time,sort_type,simple_return,is_processed);
 }
 
 /**
  * 添加一个新的entity，一个entity可以是一个人、一辆车、或者任何运动的物体
  * @author 郭楠 20160411
  * @param ak 百度申请的ak
  * @param service_id service的ID，作为其唯一标识
  * @param entity_name 要添加的一个实体的名称
  * @return void
  */
 public static void addEntity(String ak,String service_id,String entity_name) throws IOException{
	 System.out.println("============开始添加entity==============");
	  HttpRequest request = new HttpRequest("http://api.map.baidu.com/trace/v2/entity/add");
	  request.setMethod(Method.POST);
	  request.addParam("ak", ak);
	  request.addParam("service_id", service_id);
	  request.addParam("entity_name", entity_name);
	  HttpResponse response = request.exeute();
	  System.out.println(response);
	  System.out.println(HttpUtils.getString(response));
	  System.out.println("============结束添加entity==============");
}
 
 
  /**
  * 批量上传坐标集合
  * @author 郭楠 20160411
  * @param ak 百度申请的ak
  * @param service_id 服务id 百度申请的
  * @param entity_name 指定的实体 如 一个人，一台车
  * @param point_list 坐标集合
  * @return void
  */
  public static void addpoints(String ak,String service_id,String entity_name,String point_list) throws IOException{
	  System.out.println("============开始批量上传坐标==============");
	  HttpRequest request = new HttpRequest("http://api.map.baidu.com/trace/v2/track/addpoints");
	  request.setMethod(Method.POST);
	  request.addParam("ak", ak);
	  request.addParam("service_id", service_id);
	  request.addParam("entity_name", entity_name);
	  request.addFile("point_list", new File(point_list));
	  HttpResponse response = request.exeute();
	  System.out.println(response);
	  System.out.println(HttpUtils.getString(response));
	  System.out.println("============结束批量上传坐标==============");
}
  
  /**
   * 单条上传坐标
   * @author 郭楠 20160411
   * @param ak 百度申请的ak
   * @param service_id 服务id 百度申请的
   * @param entity_name 指定的实体 如 一个人，一台车
   * @param coord_type 1：GPS经纬度坐标2：国测局加密经纬度坐标 3：百度加密经纬度坐标。
   * @param latitude 纬度
   * @param longitude 经度
   * @param loc_time 轨迹点采集的GPS时间
   * @return void
   */
  public static void addpoint(String ak,String service_id,String entity_name,String coord_type,
		  String latitude,String longitude,String loc_time)
		  throws IOException{
	  System.out.println("============开始单条上传坐标==============");
	  HttpRequest request = new HttpRequest("http://api.map.baidu.com/trace/v2/track/addpoint");
	  request.setMethod(Method.POST);
	  request.addParam("ak", ak);
	  request.addParam("service_id", service_id);
	  request.addParam("entity_name", entity_name);
	  request.addParam("latitude", latitude);
	  request.addParam("longitude", longitude);
	  request.addParam("coord_type", coord_type);
	  request.addParam("loc_time", loc_time);
	  HttpResponse response = request.exeute();
	  System.out.println(response);
	  System.out.println(HttpUtils.getString(response));
	  System.out.println("============结束单条上传坐标==============");
  }
  
  /**
   * 查询所有的entity
   * @author 郭楠 20160411
   * @param ak 百度申请的ak
   * @param service_id 服务id 百度申请的
   * @param return_type 0代表返回全部结果，1代表只返回entity_name字段。默认值为0。
   * @return void
   */
  public static void listEntity(String ak,String service_id,String return_type) throws IOException{
	  System.out.println("============开始查询所有的entity==============");
	  HttpRequest request = new HttpRequest("http://api.map.baidu.com/trace/v2/entity/list");
	  request.setMethod(Method.GET);
	  request.addParam("ak", ak);
	  request.addParam("service_id", service_id);
	  request.addParam("return_type", return_type);
	  HttpResponse response = request.exeute();
	  System.out.println(response);
	  System.out.println(HttpUtils.getString(response));
	  System.out.println("============结束查询所有的entity==============");
  }
  
  /**
   * 查询指定的entity
   * @author 郭楠 20160411
   * @param ak 百度申请的ak
   * @param service_id 服务id 百度申请的
   * @param entity_name 要检索的实体名称
   * @param return_type 0代表返回全部结果，1代表只返回entity_name字段。默认值为0。
   * @return void
   */
  public static void serviceEntity(String ak,String service_id,String entity_name,String return_type) throws IOException{
	  System.out.println("============开始查询指定的entity==============");
	  HttpRequest request = new HttpRequest("http://api.map.baidu.com/trace/v2/entity/list");
	  request.setMethod(Method.GET);
	  request.addParam("ak", ak);
	  request.addParam("service_id", service_id);
	  request.addParam("entity_names", entity_name);
	  request.addParam("return_type", return_type);
	  HttpResponse response = request.exeute();
	  System.out.println(response);
	  System.out.println(HttpUtils.getString(response));
	  System.out.println("============结束查询指定的entity==============");
  }
  
  /**
   * 查询历史轨迹
   * @author 郭楠 20160411
   * @param ak 百度申请的ak
   * @param service_id 服务id 百度申请的
   * @param entity_name 指定的实体 如 一个人，一台车
   * @param start_time 起始时间
   * @param end_time 结束时间
   * @param sort_type 默认值是0：返回轨迹点按loc_time从大到小排序；当设为1时，则反之。
   * @param simple_return 默认值是0，当设为1时，仅返回轨迹点的经纬度。
   * @param is_processed 默认值是0。0为返回原始轨迹，1为返回纠偏轨迹。
   *                     注意：轨迹纠偏功能包括去噪、抽稀、绑路三个步骤，
   *                     当is_processed=1时，默认仅进行去噪和抽稀处理
   *                     ，并不作绑路处理。若应用为车辆轨迹追踪类型，需要开通绑路操作，
   *                     可发邮件至：baiduyingyan@baidu.com，
   *                     注明：ak、service_id和同时在线车辆数，申请开通。
   * @return void
   */
  public static void gethistory(String ak,String service_id,String entity_name,String start_time,String end_time,
		  String sort_type,String simple_return,String is_processed)
		  throws IOException{
	  System.out.println("============开始查询历史轨迹==============");
	  HttpRequest request = new HttpRequest("http://api.map.baidu.com/trace/v2/track/gethistory");
	  request.setMethod(Method.GET);
	  request.addParam("ak", ak);
	  request.addParam("service_id", service_id);
	  request.addParam("entity_name", entity_name);
	  request.addParam("start_time", start_time);
	  request.addParam("end_time", end_time);
	  request.addParam("sort_type", sort_type);
	  request.addParam("simple_return", simple_return);
	  request.addParam("is_processed", is_processed);
	  HttpResponse response = request.exeute();
	  System.out.println(response);
	  System.out.println(HttpUtils.getString(response));
	  System.out.println("============结束查询历史轨迹==============");
  }
  
  /**
   * 删除指定的entity
   * @author 郭楠 20160411
   * @param ak 百度申请的ak
   * @param service_id 服务id 百度申请的
   * @param entity_name entity名称，作为其唯一标识。
   * @return void
   */
  public static void deleteEntity(String ak,String service_id,String entity_name) throws IOException{
	  System.out.println("============开始删除指定的entity==============");
	  HttpRequest request = new HttpRequest("http://api.map.baidu.com/trace/v2/entity/delete");
	  request.setMethod(Method.POST);
	  request.addParam("ak", ak);
	  request.addParam("service_id", service_id);
	  request.addParam("entity_name", entity_name);
	  HttpResponse response = request.exeute();
	  System.out.println(response);
	  System.out.println(HttpUtils.getString(response));
	  System.out.println("============结束删除指定的entity==============");
  }
  
  
}