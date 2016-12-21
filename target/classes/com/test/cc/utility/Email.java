package com.test.cc.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class Email {
	private static Logger Log = Logger.getLogger(Email.class.getName());

	/*public static void sendMailWithAuth(String host, String user, String password, 
		    String port, List<String> toList, String htmlBody, 
		        String subject) throws Exception {

		    Properties props = System.getProperties();

		    props.put("mail.smtp.user",user); 
		    props.put("mail.smtp.password", password);
		    props.put("mail.smtp.host", host); 
		    props.put("mail.smtp.port", port); 
		    //props.put("mail.debug", "true"); 
		    props.put("mail.smtp.auth", "true"); 
		   props.put("mail.smtp.starttls.enable","true"); 
		    props.put("mail.smtp.EnableSSL.enable","true");

		    Session session = Session.getInstance(props, null);
		    //session.setDebug(true);

		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(user));

		    // To get the array of addresses
		    for (String to: toList) {
		        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		    }

		    message.setSubject(subject);
		    message.setContent(htmlBody, "text/html");

		    Transport transport = session.getTransport("smtp");
		    try {
		        transport.connect(host, user, password);
		        transport.sendMessage(message, message.getAllRecipients());
		    } finally {
		        transport.close();
		    }
		}*/

	private static String readAttachmentFile(String ProjectPath,String testResultFolderName)
	{
		StringBuilder fileNametemp= new StringBuilder(ProjectPath);
		fileNametemp.append(Constants.TEST_OUTPUT_FOLDER);
		fileNametemp.append(File.separator);
		fileNametemp.append(testResultFolderName);
		fileNametemp.append(Constants.TESTNG_ATTACHMENT_FILE);

		return fileNametemp.toString();

	}

	public static boolean sendMailNow(String toMail, String host, String uname, String pwd, String port)
			throws Exception
	{
		boolean isMailSentSucessfully=false;

		Properties props = new Properties();
		props.put("mail.smtp.host",host);
		if(host.equalsIgnoreCase("smtp.gmail.com"))
		{
			props.put("mail.smtp.socketFactory.port",port);
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port",port);

		}
		else
		{
			props.put("mail.smtp.auth", "true");
		}
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(uname,pwd);
			}
		});

		String filename = null;

		filename =readAttachmentFile(Constants.ProjectPath,Constants.testResultFolderName);
		File attachmentFile=new File(filename);
		String UserOs=System.getProperty("os.name");
		
		/**Capture time and date**/
		DateFormat dateFormat = new SimpleDateFormat("MMMMM d yyyy, K.mm a");
		Date date = new Date();

		/**capture the date**/
		String current_Time = dateFormat.format(date);
		String NewFolderName="Report generated on date "+current_Time+" On "+UserOs+""+Constants.testResultFolderName+" Platform";

		if(attachmentFile.exists())
		{
			try {
			
				System.out.println("Mail session object-Performing step 1.");
				MimeMessage message = new MimeMessage(session);
				System.out.println("Mail session object-Performing step 2.");
				message.setFrom(new InternetAddress(uname));
				System.out.println("Mail session object-Performing step 3.");
				message.addRecipient(Message.RecipientType.TO,new InternetAddress(toMail));
				System.out.println("Mail session object-Performing step 4.");
				message.setSubject(NewFolderName);
				System.out.println("Composing message body.");

				StringBuilder htmlStreamBuilder = new StringBuilder();// read html file into a string so that we can preview html in mail.......

				htmlStreamBuilder.append("<h2 style="+"font-family:Verdana;>"+"Test Suite run has been completed sucessfully.</h2>");
				htmlStreamBuilder.append("<br>");
				htmlStreamBuilder.append("<h3 style="+"font-family:Verdana;>"+"Please find attachment for report sent from location:<div style="+"color:#696969;>"+filename+"</div></h3>");

				htmlStreamBuilder.append("<br>");
				//htmlStreamBuilder.append("<h2 style="+"font-family:Verdana;>"+"Preview of attached report:</h2>");
				//htmlStreamBuilder.append("<br>");
/*
				try {
					BufferedReader in = new BufferedReader(new FileReader(filename));
					String str;
					while ((str = in.readLine()) != null) {
						htmlStreamBuilder.append(str);
					}
					in.close();
				} catch (IOException e) {
				}*/
				htmlStreamBuilder.append("<br><br><h2 style="+"color:#DC143C;font-family:Verdana;>"+"**NOTE:-This is an Auto Generated System E-Mail.Please do not reply **</h2>");
				String htmlStream = htmlStreamBuilder.toString();

			    Multipart multipart = new MimeMultipart();
			
				MimeBodyPart htmlPart = new MimeBodyPart();
				MimeBodyPart attachmentPart= new MimeBodyPart();

				DataSource source = new FileDataSource(filename);
				attachmentPart.setDataHandler(new DataHandler(source));
				attachmentPart.setFileName(Constants.TESTNG_ATTACHMENT_FILE);

				htmlPart.setContent(htmlStream, "text/html; charset=utf-8");

				
				multipart.addBodyPart(htmlPart);
				multipart.addBodyPart(attachmentPart);

				message.setContent(multipart);

				Transport.send(message);
				System.out.println("mail with attachment has been sent successfully from location :"+filename);
				Log.info("mail with attachment has been sent successfully from location :"+filename);

				isMailSentSucessfully = true;

			} catch (MessagingException e)
			{
				Log.info("Issue in message sending, MessagingExceptionn raised.");
				System.out.println("Issue in message sending, MessagingExceptionn raised.");
				
				throw e;
			}
		}
		else
		{
			System.out.println("Attachmment file does not exist.");
			Log.info("Attachmment file does not exist.");
		}

		return isMailSentSucessfully;

	}
}