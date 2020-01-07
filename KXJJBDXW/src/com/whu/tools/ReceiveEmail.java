package com.whu.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.whu.web.email.EmailBean;
import com.whu.web.email.RecvMailInfo;

/**
 * �����ʼ�������
 * 
 * */
public class ReceiveEmail {
    
    private MimeMessage msg = null;
    private String saveAttchPath = "";
    private StringBuffer bodytext = new StringBuffer();
    private String dateformate = "yy-MM-dd HH:mm";
    
    public ReceiveEmail(MimeMessage msg){
        this.msg = msg;
        }
    public void setMsg(MimeMessage msg) {
        this.msg = msg;
    }
    
    public ReceiveEmail()
    {
    	
    }
    
    /**
     * ��ȡ�����ʼ�����Ϣ
     * @return
     * @throws MessagingException
     */
    public String getFrom() throws MessagingException{
        InternetAddress[] address = (InternetAddress[]) msg.getFrom();
        String from = address[0].getAddress();
        if(from == null){
            from = "";
        }
        String personal = address[0].getPersonal();
        if(personal == null){
            personal = "";
        }
        String fromaddr = personal +"<"+from+">";
        return fromaddr;
    }
    
    /**
     * ��ȡ�ʼ��ռ��ˣ����ͣ����͵ĵ�ַ����Ϣ�����������ݵĲ�����ͬ "to"-->�ռ���,"cc"-->�����˵�ַ,"bcc"-->���͵�ַ
     * @param type
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public String getMailAddress(String type) throws MessagingException, UnsupportedEncodingException{
        String mailaddr = "";
        String addrType = type.toUpperCase();
        InternetAddress[] address = null;
        
        if(addrType.equals("TO")||addrType.equals("CC")||addrType.equals("BCC")){
            if(addrType.equals("TO")){
                address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.TO);
            }
            if(addrType.equals("CC")){
                address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.CC);
            }
            if(addrType.equals("BCC")){
                address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.BCC);
            }
            
            if(address != null){
                for(int i=0;i<address.length;i++){
                    String mail = address[i].getAddress();
                    if(mail == null){
                        mail = "";
                    }else{
                        mail = MimeUtility.decodeText(mail);
                    }
                    String personal = address[i].getPersonal();
                    if(personal == null){
                        personal = "";
                    }else{
                        personal = MimeUtility.decodeText(personal);
                    }
                    String compositeto = personal +"<"+mail+">";
                    mailaddr += ","+compositeto; 
                }
                mailaddr = mailaddr.substring(1);
            }
        }else{
            throw new RuntimeException("Error email Type!");
        }
        return mailaddr;
    }
    
    /**
     * ��ȡ�ʼ�����
     * @return
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public String getSubject() throws UnsupportedEncodingException, MessagingException{
        String subject = "";
        subject = MimeUtility.decodeText(msg.getSubject());
        if(subject == null){
            subject = "";
        }
        return subject;
    }
    
    /**
     * ��ȡ�ʼ���������
     * @return
     * @throws MessagingException
     */
    public String getSendDate() throws MessagingException{
        Date sendDate = msg.getSentDate();
        SimpleDateFormat smd = new SimpleDateFormat(dateformate);
        return smd.format(sendDate);
    }
    
    /**
     * ��ȡ�ʼ���������
     * @return
     */
    public String getBodyText(){
        
        return bodytext.toString();
    }
    
    /**
     * �����ʼ������õ����ʼ����ݱ��浽һ��stringBuffer�����У������ʼ� ��Ҫ����MimeType�Ĳ�ִͬ�в�ͬ�Ĳ�����һ��һ���Ľ���
     * @param part
     * @throws MessagingException
     * @throws IOException
     */
    public void getMailContent(Part part) throws MessagingException, IOException{
        
        String contentType = part.getContentType();
        int nameindex = contentType.indexOf("name");
        boolean conname = false;
        if(nameindex != -1){
            conname = true;
        }
        //System.out.println("CONTENTTYPE:"+contentType);
        if(part.isMimeType("text/plain")&&!conname){
            bodytext.append((String)part.getContent());
        }else if(part.isMimeType("text/html")&&!conname){
            bodytext.append((String)part.getContent());
        }else if(part.isMimeType("multipart/*")){
            Multipart multipart = (Multipart) part.getContent();
            int count = multipart.getCount();
            for(int i=0;i<count;i++){
                getMailContent(multipart.getBodyPart(i));
            }
        }else if(part.isMimeType("message/rfc822")){
            getMailContent((Part) part.getContent()); 
        }
        
    }
    
    /**
     * �ж��ʼ��Ƿ���Ҫ��ִ�������ִ����true�����򷵻�false
     * @return
     * @throws MessagingException
     */
    public String getReplySign() throws MessagingException{
        String replySign = "0";
        String needreply[] = msg.getHeader("Disposition-Notification-TO");
        if(needreply != null){
            replySign = "1";
        }
        return replySign;
    }
    
    /**
     * ��ȡ���ʼ���message-id
     * @return
     * @throws MessagingException
     */
    public String getMessageId() throws MessagingException{
        return msg.getMessageID();
    }
    
    /**
     * �жϴ��ʼ��Ƿ��Ѷ������δ���򷵻�false���Ѷ�����true
     * @return
     * @throws MessagingException
     */
    public String isNew() throws MessagingException{
        String isnew = "0";
        Flags flags = ((Message)msg).getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        for(int i=0;i<flag.length;i++){
            if(flag[i]==Flags.Flag.SEEN){
                isnew = "1";
                break;
            }
        }
        
        return isnew;
    }
    
    /**
     * �ж����Ƿ��������
     * @param part
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    public boolean isContainAttch(Part part) throws MessagingException, IOException{
        boolean flag = false;
        
        String contentType = part.getContentType();
        if(part.isMimeType("multipart/*")){
            Multipart multipart = (Multipart) part.getContent();
            int count = multipart.getCount();
            for(int i=0;i<count;i++){
                BodyPart bodypart = multipart.getBodyPart(i);
                String dispostion = bodypart.getDisposition();
                if((dispostion != null)&&(dispostion.equals(Part.ATTACHMENT)||dispostion.equals(Part.INLINE))){
                    flag = true;
                }else if(bodypart.isMimeType("multipart/*")){
                    flag = isContainAttch(bodypart);
                }else{
                    String conType = bodypart.getContentType();
                    if(conType.toLowerCase().indexOf("appliaction")!=-1){
                        flag = true;
                    }
                    if(conType.toLowerCase().indexOf("name")!=-1){
                        flag = true;
                    }
                }
            }
        }else if(part.isMimeType("message/rfc822")){
            flag = isContainAttch((Part) part.getContent());
        }
        
        return flag;
    }
    
    /**
     * ���渽��
     * @param part
     * @throws MessagingException
     * @throws IOException
     */
    public void saveAttchMent(Part part) throws MessagingException, IOException{
        String filename = "";
        if(part.isMimeType("multipart/*")){
            Multipart mp = (Multipart) part.getContent();
            for(int i=0;i<mp.getCount();i++){
                BodyPart mpart = mp.getBodyPart(i);
                String dispostion = mpart.getDisposition();
                if((dispostion != null)&&(dispostion.equals(Part.ATTACHMENT)||dispostion.equals(Part.INLINE))){
                    filename = mpart.getFileName();
                    if(filename.toLowerCase().indexOf("gb2312")!=-1){
                        filename = MimeUtility.decodeText(filename);
                    }
                    saveFile(filename,mpart.getInputStream());
                }else if(mpart.isMimeType("multipart/*")){
                    saveAttchMent(mpart);
                }else{
                    filename = mpart.getFileName();
                    if(filename != null&&(filename.toLowerCase().indexOf("gb2312")!=-1)){
                        filename = MimeUtility.decodeText(filename);
                    }
                    saveFile(filename,mpart.getInputStream());
                }
            }
            
        }else if(part.isMimeType("message/rfc822")){
            saveAttchMent((Part) part.getContent());
        }
    }
    /**
     * ��ñ��渽���ĵ�ַ
     * @return
     */
    public String getSaveAttchPath() {
        return saveAttchPath;
    }
    /**
     * ���ñ��渽����ַ
     * @param saveAttchPath
     */
    public void setSaveAttchPath(String saveAttchPath) {
        this.saveAttchPath = saveAttchPath;
    }
    /**
     * �������ڸ�ʽ
     * @param dateformate
     */
    public void setDateformate(String dateformate) {
        this.dateformate = dateformate;
    }
    /**
     * �����ļ�����
     * @param filename
     * @param inputStream
     * @throws IOException
     */
    private void saveFile(String filename, InputStream inputStream) throws IOException {
        String osname = System.getProperty("os.name");
        String storedir = getSaveAttchPath();
        String sepatror = "";
        if(osname == null){
            osname = "";
        }
        
        if(osname.toLowerCase().indexOf("win")!=-1){
            sepatror = "//";
            if(storedir==null||"".equals(storedir)){
                storedir = "d://temp";
            }
        }else{
            sepatror = "/";
            storedir = "/temp";
        }
        
        File storefile = new File(storedir+sepatror+filename);
        System.out.println("storefile's path:"+storefile.toString());
        
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        
        try {
            bos = new BufferedOutputStream(new FileOutputStream(storefile));
            bis = new BufferedInputStream(inputStream);
            int c;
            while((c= bis.read())!=-1){
                bos.write(c);
                bos.flush();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            bos.close();
            bis.close();
        }
        
    }
    
    public void recive(Part part,int i) throws MessagingException, IOException{
        System.out.println("------------------START-----------------------");
        System.out.println("Message"+i+" subject:" + getSubject());
        System.out.println("Message"+i+" from:" + getFrom());
        System.out.println("Message"+i+" isNew:" + isNew());
        boolean flag = isContainAttch(part);
        System.out.println("Message"+i+" isContainAttch:" +flag);
        System.out.println("Message"+i+" replySign:" + getReplySign());
        getMailContent(part);
        System.out.println("Message"+i+" content:" + getBodyText());
        /*
        setSaveAttchPath("c://temp//"+i);
        if(flag){
            saveAttchMent(part);
        }
        */
        System.out.println("------------------END-----------------------");
    }
    
    /*
     * 
     */
    public RecvMailInfo getEmailInfo(Part part,int i) throws Exception{
    	String title = getSubject();
    	String sendName = getFrom();
    	String isRead = "0";
    	String needReply = getReplySign();
    	String content = getBodyText();
    	String recvName = getMailAddress("to");
    	String csName = getMailAddress("cc");
    	String asName = getMailAddress("bcc");
    	String sendTime = getSendDate();
    	String emailID = getMessageId();
    	
    	Date currentTime = new Date();
		SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd");
		String recvTime = forma.format(currentTime);
		
		RecvMailInfo rmInfo = new RecvMailInfo();
		rmInfo.setSendName(sendName);
		rmInfo.setRecvName(recvName);
		rmInfo.setCsName(csName);
		rmInfo.setAsName(asName);
		rmInfo.setIsRead(isRead);
		rmInfo.setNeedReply(needReply);
		rmInfo.setContent(content);
		rmInfo.setSendTime(sendTime);
		rmInfo.setRecvTime(recvTime);
		rmInfo.setTitle(title);
		rmInfo.setEmailID(emailID);
    	return rmInfo;
    }
    /**
     * ��������������Ϣ��ȡ���յ����ʼ��б�
     * */
    public ArrayList RecvEmailList(EmailBean emailBean) throws Exception {
		String mailAddress = emailBean.getMailBoxAddress();
		String pwd = emailBean.getMailBoxPwd();
		String popPC = emailBean.getPopPC();
		int popPort = Integer.parseInt(emailBean.getPopPort());
		String mailBoxType = emailBean.getMailBoxType();
		
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		URLName urlname = new URLName(mailBoxType, popPC, popPort, null, mailAddress, pwd);

		Store store = session.getStore(urlname);
		store.connect();
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		Message msgs[] = folder.getMessages();
		int count = msgs.length;

		ArrayList resultList = new ArrayList();
		RecvMailInfo rmInfo = null;
		ReceiveEmail rm = null;
		String emailID = "";
		DBTools dbTools = new DBTools();
		boolean checkResult = false;
		for (int i = 0; i < count; i++) {
			rm = new ReceiveEmail((MimeMessage) msgs[i]);
			//rm.recive(msgs[i], i);
			rmInfo = rm.getEmailInfo(msgs[i], i);
			emailID = rmInfo.getEmailID();
			//�ж��ʼ��Ƿ��Ѿ��������ݿ���
			if(i != count - 1)
			{
				checkResult = dbTools.checkEmail(emailID, false);
			}
			else
			{
				checkResult = dbTools.checkEmail(emailID, true);
			}
			if(!checkResult)
			{
				resultList.add(rmInfo);
			}
		}

		return resultList;
	}
    
    /*
    public static void main(String[] args) throws MessagingException, IOException {
        Properties props = new Properties();
        //props.setProperty("mail.smtp.host", "smtp.163.com");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props,null);
		//session.setDebug(true);
        URLName urlname = new URLName("pop3","pop.163.com",110,null,"lvww_konghen@163.com","lvww3951345");
        
        Store store = session.getStore(urlname);
        store.connect();
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message msgs[] = folder.getMessages();
        int count = msgs.length;
        System.out.println("Message Count:"+count);
        
        ReceiveEmail rm = null;
        for(int i=0;i<count;i++){
            rm = new ReceiveEmail((MimeMessage) msgs[i]);
            rm.recive(msgs[i],i);;
        }
        
    }
    */
    
}