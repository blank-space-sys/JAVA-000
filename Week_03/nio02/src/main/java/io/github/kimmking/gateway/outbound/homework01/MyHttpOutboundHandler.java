package io.github.kimmking.gateway.outbound.homework01;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.filter.impl.MyFilterImpl;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Author snail
 * @Description
 * @Date 2020-11-04 20:52
 **/
public class MyHttpOutboundHandler {
    private String backendUrl;

    // 1,创建一个httpClient对象
    private CloseableHttpClient client;
    // 2,创建uriBuilder 对于httpClient4.3访问指定页面url必须要使用http://开始
    private URIBuilder uriBuilder ;



    public MyHttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl;
        client = HttpClients.createDefault();

    }
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) throws Exception{
        uriBuilder = new URIBuilder(backendUrl);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        // 5,设置请求报文头部的编码
        httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; utf-8"));
        // 6,设置期望服务返回的编码
        httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
        // 额外添加请求头参数
        httpGet.setHeader(new BasicHeader("nio",fullRequest.headers().get("nio")));
        // 7，请求服务
        CloseableHttpResponse response = client.execute(httpGet);
        handleResponse(fullRequest, ctx, response);
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) throws Exception {
        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }
    }
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
