package Servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entity.ChatMessage;
import Entity.ChatUser;


public class MessageListServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;
    
    
    public void init() throws ServletException {
    	// Вызвать унаследованную от HttpServlet версию init()
    	super.init();
    
    	BufferedReader reader;
    	
    	try {
			reader = new BufferedReader(new FileReader("D:\\Учёба\\4 семестр\\Java\\Laba8_true\\Users.txt"));
			String line = new String();
			do {
				String name = reader.readLine();
				String sesion = reader.readLine();
				if(name == null) {
					break;
				}
				activeUsers.put(name, new ChatUser(name, sesion));
				line = sesion;
			} while (line != null);

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	Iterator it = (Iterator) activeUsers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey());
            System.out.println(pair.getValue());
        }

		try {
			reader = new BufferedReader(new FileReader("D:\\Учёба\\4 семестр\\Java\\Laba8_true\\Mesanges.txt"));
			String line = new String();
			do {
				String msg = reader.readLine();
				if(msg == null) {
					break;
				}
				String authorName = reader.readLine();
				
				messages.add(new ChatMessage(msg, activeUsers.get(authorName),
						Long.valueOf(reader.readLine())));
				line = msg;
			} while (line != null);

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < messages.size(); i++) {
			System.out.println(messages.get(i).getMessage());
			System.out.println(messages.get(i).getTimestamp());
		}
    	
    	        
    	        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
// Установить кодировку HTTP-ответа UTF-8
        response.setCharacterEncoding("utf8");
// Получить доступ к потоку вывода HTTP-ответа
        PrintWriter pw = response.getWriter();
// Записть в поток HTML-разметку страницы
        pw.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><meta http-equiv='refresh' content='10'></head>");
        pw.println("<body>");
        
// В обратном порядке записать в поток HTML-разметку для каждого сообщения
        try(FileWriter writer = new FileWriter("D:\\Учёба\\4 семестр\\Java\\Laba8_true\\Mesanges.txt", false)){
	        for (int i=messages.size()-1; i>=0; i--) {
	            ChatMessage aMessage = messages.get(i);
	        	pw.println("<div><strong>" + aMessage.getAuthor().getName()
	                    + "</strong>: " + aMessage.getMessage() + "</div>");
	        	writer.write(messages.get(i).getMessage());
     			writer.write("\n");
     			writer.write(String.valueOf(messages.get(i).getAuthor().getName()));
     			writer.write("\n");
     			writer.write(String.valueOf(messages.get(i).getTimestamp()));
     			writer.write("\n");
	        }
	        writer.flush();
	        pw.println("</body></html>");
        }catch(IOException ex){
            
            System.out.println(ex.getMessage());
        } 
     		
     	
        
        try(FileWriter writer = new FileWriter("D:\\Учёба\\4 семестр\\Java\\Laba8_true\\Users.txt", false)){
        	 Iterator it = (Iterator) activeUsers.entrySet().iterator();
             while (it.hasNext()) {
                 Map.Entry pair = (Map.Entry)it.next();
                 writer.write(String.valueOf(pair.getKey()));
                 writer.write("\n");
                 ChatUser cu = (ChatUser) pair.getValue();
                 writer.write(cu.getSessionId());
                 writer.write("\n");
             }
             writer.flush();
     		
     	}catch(IOException ex){
             
            System.out.println(ex.getMessage());
        }
        
    }
    
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             super.doDelete(request, response);
             
             try(FileWriter writer = new FileWriter("D:\\Учёба\\4 семестр\\Java\\Laba8_true\\Mesanges.txt", false)){
     	        for (int i=messages.size()-1; i>=0; i--) {
     	            ChatMessage aMessage = messages.get(i);
     	        	writer.write(messages.get(i).getMessage());
          			writer.write("\n");
          			writer.write(String.valueOf(messages.get(i).getAuthor().getName()));
          			writer.write("\n");
          			writer.write(String.valueOf(messages.get(i).getTimestamp()));
          			writer.write("\n");
     	        }
     	        writer.flush();
             }catch(IOException ex){
                 
                 System.out.println(ex.getMessage());
             } 
             
             try(FileWriter writer = new FileWriter("D:\\Учёба\\4 семестр\\Java\\Laba8_true\\Users.txt", false)){
            	 Iterator it = (Iterator) activeUsers.entrySet().iterator();
                 while (it.hasNext()) {
                     Map.Entry pair = (Map.Entry)it.next();
                     writer.write(String.valueOf(pair.getKey()));
                     writer.write("\n");
                     ChatUser cu = (ChatUser) pair.getValue();
                     writer.write(cu.getSessionId());
                     writer.write("\n");
                 }
                 writer.flush();
         		
         	}catch(IOException ex){
                 
                System.out.println(ex.getMessage());
            }
        }
}
