package edu.cmu.sv.ws.ssnoc.test;

import static com.eclipsesource.restfuse.Assert.*;
import static org.junit.Assert.*;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;




@RunWith(HttpJUnitRunner.class)
public class MessageServiceIT {
	
	@Rule
	public Destination destination = new Destination(this,
			"http://localhost:1234/ssnoc");
	
	@Context
	public Response response;
	
	@Before
	public void setUp() throws Exception {
//		TestService.addTestUser("tester1");
//		TestService.addTestUser("tester2");
//		TestService.postTestWallMessage("wall", "tester1");
//		TestService.postTestPrivateMessage("tester1", "tester2", "private");
//		String addUserUrl = "http://localhost:1234/ssnoc/user/signup";
//		String sendWallMessage = "http://localhost:1234/ssnoc/message/tester1";
//		String sendPrivateMessage = "http://localhost:1234/ssnoc/message/tester1/tester2";
//		URL url = new URL(addUserUrl);
//		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//		connection.setDoOutput(true);
//		connection.setDoInput(true);
//		connection.setRequestMethod("POST");
//		connection.setUseCaches(false);
//		connection.setInstanceFollowRedirects(true);
//		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//		connection.connect();
//		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//		String content = "{\"userName\":\"tester1\", \"password\";\"123456\"}";
//		out.writeBytes(content);
//		out.flush();
//		out.close();
		
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	
	//post a wall message
	@HttpTest(method = Method.POST, path = "/message/tester1", type = MediaType.APPLICATION_JSON, 
			content = "{\"author\":\"qihao\",\"content\":\"Hello\",\"postedAt\":\"2014-11-22 19:30:22\"}")
	public void testSaveWallMessage()
	{
		assertCreated(response);
	}
	
	//post a private message from tester1 to tester2
	@HttpTest(method = Method.POST, path = "/message/tester1/tester2", type = MediaType.APPLICATION_JSON, 
			content = "{\"content\":\"Hello\",\"postedAt\":\"2014-11-22 19:30:22\"}")
	public void testSaveChatMessage()
	{
		System.out.println(response.getBody());
		System.out.println(response.getStatus());
		assertCreated(response);
	}
	
	//get a message whose id is 1
	@HttpTest(method = Method.GET, path = "/message/1")
	public void testGetmessageById() {
		if(response.getBody().equals(""))
			assertNoContent(response);
		else
			assertOk(response);
		//assertNoContent(response);
		//Assert.assertTrue(response.getBody().equals(""));
	}
	
	//get all wall messages
	@HttpTest(method = Method.GET, path = "/messages/wall")
	public void testLoadWallMessages()
	{
		assertOk(response);
	}
	
	//get private messages between tester1 and tester2
	@HttpTest(method = Method.GET, path = "/messages/tester1/tester2")
	public void testLoadPrivateChatMessages()
	{
		assertOk(response);
	}
	
	//get private messages from two users, from whom at least 1 user name is wrong
	//the result should be []
	@HttpTest(method = Method.GET, path = "/messages/tester1000/tester2000")
	public void testLoadPrivateChatMessagesWithWrongUserName()
	{
		Assert.assertEquals("[]", response.getBody());
	}
	/*
	@HttpTest(method = Method.POST, path = "/message/qihao", type = MediaType.APPLICATION_JSON, 
			content = "{\"author\":\"qihao\",\"content\":\"');select * from user where uid = 100;\",\"postedAt\":\"2014-11-22 19:30:22\"}")
	public void testSaveWallMessageWithBug()
	{
		assertBadRequest(response);
	}*/
}
