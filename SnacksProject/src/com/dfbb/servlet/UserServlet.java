package com.dfbb.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.dfbb.entity.Address;
import com.dfbb.entity.PersonAttrs;
import com.dfbb.entity.User;

import com.dfbb.server.UserServer;

import com.dfbb.serverImpl.UserServerImpl;

public class UserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}
	public static String md5ToUpperCase(String str){	
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char nowChar=str.charAt(i);
			if (nowChar>='a'&&nowChar<='z') {
				int num=nowChar;
				nowChar= (char)(num-32);
				sb.append(nowChar);
				continue;
			}
			sb.append(nowChar);
		}
		
		return sb.toString();
	}
	 public static String bytesToHex(byte[] bytes) {  
	        StringBuffer hexStr = new StringBuffer();  
	        int num;  
	        for (int i = 0; i < bytes.length; i++) {  
	            num = bytes[i];  
	             if(num < 0) {  
	                 num += 256;  
	            }  
	            if(num < 16){  
	                hexStr.append("0");  
	            }  
	            hexStr.append(Integer.toHexString(num));  
	        }  
	        return hexStr.toString().toUpperCase();  
	    }  
	//生成MD5  
    public static String getMD5(String message) {  
        String md5 = "";  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象  
            byte[] messageByte = message.getBytes("UTF-8");  
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位  
            md5 = bytesToHex(md5Byte);                            // 转换为16进制字符串  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return md5;  
    }  
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getContextPath();
		String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/";
		
		HttpSession session= req.getSession();
		// 得到全局作用域
		ServletContext application = req.getSession().getServletContext();
		String type = req.getParameter("opr");
		
		// 当opr为注册时
		if (type.equals("register")) {
			String userName = null;
			String passWord = null;
			Date date = null;
			String sex = null;
			String phoneNum = null;
			int provinceId = -1;
			int cityId = -1;
			int areaId = -1;
			String street = null;
			FileItem fileItem = null;
			String iconPath = null;
			String realName = "";
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(1024 * 1024);
			boolean flag = ServletFileUpload.isMultipartContent(req);// 是否是一次文件上传

			
			try {
				if (flag) {
					List<FileItem> items = upload.parseRequest(req);
					for (FileItem item : items) {
						boolean isFormField = item.isFormField();
						if (isFormField) {
							// 是普通组件，拿到账号密码:
							String name = item.getFieldName();
							switch (name) {
							case "userName":
								userName = item.getString("utf-8");
								break;
							case "password":
								passWord = item.getString("utf-8");
								passWord=getMD5(passWord);
								break;
							case "birthday":
								String dateString= item.getString();
								SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
								date= sdf.parse(dateString);
								
								
								break;
							case "sex":
								sex = item.getString();
								break;
							case "phoneNum":
								phoneNum = item.getString();
								break;
							case "province":
								provinceId = Integer.parseInt(item.getString());
								break;
							case "city":
								cityId = Integer.parseInt(item.getString());
								break;
							case "area":
								areaId = Integer.parseInt(item.getString());
								break;
							case "street":
								street = item.getString("utf-8");
								break;
							case "realName":
								realName = item.getString("utf-8");
								break;
							}
						} else {
							// 如果是文件的话
							fileItem = item;
						}
					}

					// 是文件组件
					String fileName = fileItem.getName();
					
					String sufName="";
					String absFoldPath = application.getRealPath("icon");// 存放图片的文件夹的真实路径
					File file=null;
					
					boolean fileIsEmpty=false;
					if (fileName == "" || fileName == null) {//当用户没有上传头像时
						fileIsEmpty=true;
						iconPath = null;
					}else {//当上传了的时候
						fileName=userName+fileName;
						file = new File(absFoldPath + "/" + fileName);
						iconPath = "/SnacksProject/icon/" + fileName;				
						
						sufName = fileName.substring(fileName.lastIndexOf("."));
					}
					
					
					List<String> typeList = Arrays.asList(".jpg", ".gif", ".bmp");
					if (fileIsEmpty==false&&typeList.contains(sufName)==false&&iconPath!=null) {//判断文件类型是否匹配

						throw new Exception();
					} 
					
					
					User user = new User();
					user.setUserName(userName);
					user.setPassword(passWord);
					user.setBirthday(date);
					user.setSex(sex);
					user.setPhoneNum(phoneNum);
					user.setRealName(realName);
					user.setIconPath(iconPath);
					user.setIsManager("N");
					user.setMoney(300);

					Address address = new Address();
					address.setCountry("country");
					address.setProvinceid(provinceId);
					address.setAreaid(areaId);
					address.setCityid(cityId);
					address.setStreet(street);
					address.setContactPerson(realName);
					address.setPhoneNum(phoneNum);
					
					UserServer userServer = new UserServerImpl();
					User user2= userServer.checkUName(userName);
					int res=0;
					if (user2==null) {
						// 获得是否插入成功的结果
						res = userServer.register(user, address);
					}else {
						res=-1;
					}
					
					if (res > 0) {// 成功。
						try {
							// 验证文件是否为空，不为空则上传
							if (fileName != "" && fileName != null) {
								fileItem.write(file);
							}
							session=req.getSession(true);
							session.setAttribute("msg", "注册成功！");
							
							User user3=userServer.getUserByUserName(userName);
							
							session.setAttribute("user", user3);
							//注册成功，重定向至欢迎页面
							resp.setStatus(302);
							resp.setHeader("location", basePath+"/onOprator/mainPage/welcome.jsp");
							
							
						} catch (Exception e) {
							session.setAttribute("msg", "注册成功！上传头像失败！");
							resp.setStatus(302);
							resp.setHeader("location", basePath+"/register/register.jsp");
						}
					} else {
						session.setAttribute("msg", "注册失败！");
						resp.setStatus(302);
						resp.setHeader("location", basePath+"/register/register.jsp");
					}

				} else {
					session.setAttribute("msg", "非法上传！");
					resp.setStatus(302);
					resp.setHeader("location", basePath+"/register/register.jsp");
				}
			} catch (FileUploadException e) {
				if (e instanceof FileUploadBase.FileSizeLimitExceededException) {
						session.setAttribute("msg", "文件超过大小！注册失败！");
						resp.setStatus(302);
						resp.setHeader("location", basePath+"/register/register.jsp");
				}
			}catch (Exception e) {
					session.setAttribute("msg","只支持jpg,gif,bmp格式");
					resp.setStatus(302);
					resp.setHeader("location", basePath+"/register/register.jsp");
			}
			

		}else if (type.equals("checkName")) {
			//查看当前用户名是否可行。
			String uname= req.getParameter("uname");
			UserServer userServer=new UserServerImpl();
			User user=userServer.checkUName(uname);
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter out =resp.getWriter();
			if (user==null) {//代表可以注册
				out.print("1");
			}else {//代表不可以注册
				out.print("0");
			}
			out.flush();
			out.close();
		}else if (type.equals("login")) {
			//得到是否记住该账号的checkbox
			String isRememberFlag= req.getParameter("isRemember");
			int setTime=0;
			if (isRememberFlag != null) {//若不为空就设置时间 10分钟
				setTime = 600;
			} else {
				setTime = 0;
			}
			//得到账号密码。
			String userName = req.getParameter("username");
			String pwd = req.getParameter("pwd");
			
			pwd=md5ToUpperCase(pwd);
			
			
			String checkCode= req.getParameter("checkCode");//拿到用户输入的验证码
			String rightCode = (String)session.getAttribute("randCheckCode");//得到此时正确的验证码
			
			User user=new User(userName,pwd);
			UserServer userServer=new UserServerImpl();
			User findUser = userServer.userLogin(user);
			if (findUser!=null&&checkCode.equalsIgnoreCase(rightCode)) {
				
				
				//登录成功，跳转到主页
				session.setAttribute("user", findUser);
				session.setMaxInactiveInterval(600);
				//登录成功,往浏览器存入cookie
				Cookie cookieU = new Cookie("uname", userName);
				Cookie cookieP = new Cookie("pwd", pwd);
				cookieU.setMaxAge(setTime);
				cookieP.setMaxAge(setTime);
				resp.addCookie(cookieU);
				resp.addCookie(cookieP);
			
				resp.setStatus(302);
				resp.setHeader("location", basePath+"onOprator/mainPage/welcome.jsp");
				
				
			}else if (findUser!=null&&(!checkCode.equalsIgnoreCase(rightCode))) {//当账号密码正确，验证码不正确的时候。
				session.setAttribute("msg", "验证码不正确!");
				resp.setStatus(302);
				resp.setHeader("location", basePath);
			}else {
				session.setAttribute("msg", "账号密码错误!");
				resp.setStatus(302);
				resp.setHeader("location", basePath);
			}
			
			//退出
		}else if (type.equals("loginout")) {
			Cookie[] cookies= req.getCookies();
			if (cookies!=null) {
				for(Cookie c:cookies){
					c.setMaxAge(0);
					resp.addCookie(c);//cookie设置存货时间为0
				}
			}
			session.invalidate();//会话失效
			resp.sendRedirect("/SnacksProject/");//回到登录界面

		}else if (type.equals("changePwd")) {
			
			String nowPassword= req.getParameter("nowPassword");
			String newPassword= req.getParameter("newPassword");
			String uidStr = req.getParameter("uid");
			nowPassword = md5ToUpperCase(nowPassword);
			newPassword = md5ToUpperCase(newPassword);
			int uid =Integer.parseInt(uidStr);
			UserServer userServer=new UserServerImpl();
			String info =userServer.changePwd(nowPassword, newPassword, uid);
			resp.setContentType("text/plain;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(info);
			out.flush();
			out.close();
			
			
		}else if (type.equals("changePersonDetail")) {
			//修改个人信息
			String uidStr = req.getParameter("uid");
			int uid =Integer.parseInt(uidStr);
			String nickName = req.getParameter("nickname");
			String realName = req.getParameter("realName");
			String sex= req.getParameter("sex");
			
			PersonAttrs personAttrs=new PersonAttrs();
			personAttrs.setNickName(nickName);
			personAttrs.setRealName(realName);
			personAttrs.setSex(sex);
			personAttrs.setUid(uid);
			UserServer userServer=new UserServerImpl();
			int res= userServer.changePersonDetals(personAttrs);
			String info="";
			if (res>0) {
				info="修改成功!";
				User user= userServer.getUserById(uid);
				session.setAttribute("user", user);
			}else {
				info="修改失败!";
			}
			resp.setContentType("text/plain;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(info);
			out.flush();
			out.close();
			
		}else if (type.equals("changeIcon")) {
			System.out.println("已进入");
		}

	}

}
