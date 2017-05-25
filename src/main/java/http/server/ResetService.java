package http.server;


import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by stpl on 25/5/17.
 */

@Path("/")
public class ResetService {
    class Req{
        public Req(long ttp, Date arrival) {
            this.ttp = ttp;
            this.arrival = arrival;
        }

        long ttp;
        Date arrival;
    }
    private static  final Map<String,Integer> dataGrid=new ConcurrentHashMap<String, Integer>();
    private static  final Map<String,LinkedList<Req>> reqHistory=new ConcurrentHashMap<String, LinkedList<Req>>();
    private static final String GET_REQ_CNT="getReqCnt";
    private static final String POST_REQ_CNT="postReqCnt";
    private static final String PUT_REQ_CNT="putReqCnt";
    private static final String DELETE_REQ_CNT="deleteReqCnt";
    static {
        dataGrid.put("getActReqCount",0);
        dataGrid.put("putActReqCount",0);
        dataGrid.put("postActReqCount",0);
        dataGrid.put("deleteActReqCount",0);
        dataGrid.put(DELETE_REQ_CNT,0);
        dataGrid.put(PUT_REQ_CNT,0);
        dataGrid.put(POST_REQ_CNT,0);
        dataGrid.put(GET_REQ_CNT,0);
        reqHistory.put("getHis",new LinkedList<Req>());
    }
    @GET
    @Path("{id : .*}")
    public String getMethod(@Context HttpServletRequest request){
        long start=System.currentTimeMillis();
        dataGrid.put("getActReqCount",dataGrid.get("getActReqCount")+1);
        dataGrid.put(GET_REQ_CNT,dataGrid.get(GET_REQ_CNT)+1);
        try {
            Thread.sleep(5000+new Random().nextInt(10000));
        } catch (InterruptedException e) {
            dataGrid.put("getActReqCount",dataGrid.get("getActReqCount")-1);
            e.printStackTrace();
        }
        dataGrid.put("getActReqCount",dataGrid.get("getActReqCount")-1);
        reqHistory.get("getHis").add(new Req((System.currentTimeMillis()-start),new Date(start)));
        return "took "+(System.currentTimeMillis()-start)+" millis to process";
    }
    @PUT
    @Path("{id : .*}")
    public String getMethod1(@Context HttpServletRequest request){
        dataGrid.put("putActReqCount",dataGrid.get("putActReqCount")+1);
        dataGrid.put(PUT_REQ_CNT,dataGrid.get(PUT_REQ_CNT)+1);

        try {
            Thread.sleep(new Random().nextInt(30));
        } catch (InterruptedException e) {
            e.printStackTrace();
            dataGrid.put("putgetActReqCount",dataGrid.get("putActReqCount")-1);
        }
        dataGrid.put("putActReqCount",dataGrid.get("putActReqCount")-1);
        return "Hello World";
    }
    @POST
    @Path("{id : .*}")
    public String getMethod2(@Context HttpServletRequest request){
        dataGrid.put("postActReqCount",dataGrid.get("postActReqCount")+1);
        dataGrid.put(POST_REQ_CNT,dataGrid.get(POST_REQ_CNT)+1);

        try {
            Thread.sleep(new Random().nextInt(30));
        } catch (InterruptedException e) {
            e.printStackTrace();
            dataGrid.put("postActReqCount",dataGrid.get("postActReqCount")-1);
        }
        dataGrid.put("postActReqCount",dataGrid.get("posReqCount")-1);
        return "Hello World";
    }
    @DELETE
    @Path("{id : .*}")
    public String getMethod3(@Context HttpServletRequest request){
        dataGrid.put("deleteActReqCount",dataGrid.get("deleteActReqCount")+1);
        dataGrid.put(DELETE_REQ_CNT,dataGrid.get(DELETE_REQ_CNT)+1);

        try {
            Thread.sleep(new Random().nextInt(30));
        } catch (InterruptedException e) {
            e.printStackTrace();
            dataGrid.put("deleteActReqCount",dataGrid.get("deleteActReqCount")-1);

        }
        dataGrid.put("deleteActReqCount",dataGrid.get("deleteActReqCount")-1);

        return "Hello World";
    }

    @GET
    @Path("status")
    public static String stat(){
      List<Object> ret=new ArrayList<Object>();
      ret.add(dataGrid);
      ret.add(reqHistory);
        return  new Gson().toJson(ret);
    }

}
