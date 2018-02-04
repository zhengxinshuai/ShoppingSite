package com.dfbb.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.dfbb.entity.User;
import com.dfbb.server.UserServer;
import com.dfbb.serverImpl.UserServerImpl;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;  

@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024,
    maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5)
@WebServlet(name="exampleServlet", urlPatterns="/fileUploadServlet")  
public class FileUploadServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext application = req.getSession().getServletContext();// 获得全局作用域
		 User user= (User)req.getSession().getAttribute("user");
		final Part filePart = req.getPart("file");
		String uidStr = req.getParameter("uid");
		int uid= Integer.parseInt(uidStr);
		
		String fileName = getFileName(filePart);
		
		OutputStream out = null;// 获取一个输出流
		InputStream filecontent = null;// 获取一个输入流
		if (fileName == ""||fileName=="[]") {// 当用户没有上传头像的时候 返回
			return;
		}
		if (user==null||user.getId()!=uid) {
			return;
		}
		String info="";
		List<String> typeList = Arrays.asList(".jpg", ".gif", ".bmp");//定义文件格式
		String sufName = fileName.substring(fileName.lastIndexOf("."));
		if (!typeList.contains(sufName)) {//如果文件格式不符合
			info="格式不匹配,请正确导入jpg/gif/bmp格式之一";
		}else {
			try {
				
				String absFoldPath = application.getRealPath("icon");// 存放图片的文件夹的真实路径		
				String userName = user.getUserName();
				long mills= System.currentTimeMillis();
				
				fileName=userName+mills+fileName;//获得最终的文件名
				String iconPath = "/SnacksProject/icon/" + fileName;
				//插入新图片
				 out = new FileOutputStream(new File(absFoldPath+File.separator+fileName));
				filecontent=filePart.getInputStream();
				int read=0;
				byte[] bytes=new byte[1024];
				
				while ((read=filecontent.read(bytes))!=-1) {
					out.write(bytes, 0, read);
				}
				
				//把用户中的iconPath进行修改
				UserServer userServer=new UserServerImpl();
				String iconPathOld=  userServer.getUserIconPath(uid);//得到用户旧的头像地址
				boolean isDeleteSuc=true;
				if (iconPathOld!=null) {
					//删除头像
					String oldFileName = iconPathOld.substring(("/SnacksProject/icon/".length()));
					isDeleteSuc= deleteFile(absFoldPath+File.separator+oldFileName);
				}
				
				
				
				int res=userServer.updateIconPath(uid, iconPath);//更新用户的头像路径
				if (isDeleteSuc==true&&res>0) {
					info="上传成功!";
					User user2= userServer.getUserById(uid);
					req.getSession().setAttribute("user", user2);
				}else {
					info="上传错误!";
				}
				
			} catch (FileNotFoundException fne) {
				info="上传错误!";
			}finally {
				if (out!=null) {
					out.close();
				}
				if (filecontent!=null) {
					filecontent.close();
				}
				
			}
			
		}
		PrintWriter writer = resp.getWriter();//得到浏览器写出流
		
		resp.setContentType("text/plain;charset=utf-8");
		
		writer.print(info);
		writer.flush();
		writer.close();
		
		
	}
	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	  /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
               System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
}
