package com.whu.tools;

public class HtmlValueNameBean {
	//ҳ����ʾ������(��ǩ)
	  private String name = null;
	  //ʵ���ύ��ֵ
	  private String value = null;;

	  /**
	   * ���췽��
	   * @param name String ����
	   * @param value String ֵ
	   */
	  public HtmlValueNameBean(String name,String value){
	    this.name =name;
	    this.value =value;
	  }
	  public String getName(){
	    return name;
	  }
	  public void setName(String name){
	    this.name = name;
	  }

	  public String getValue(){
	    return value;
	  }
	  public void setValue(String value){
	    this.value = value;
	  }
	  /**
	   * �Ƚ��Ƿ����
	   * @param o Object
	   * @return boolean
	   */
	  public boolean equals(Object o){
	    HtmlValueNameBean tmp = (HtmlValueNameBean)o;
	    if(this.value.equals(tmp.getValue())){
	      return true;
	    }else{
	      return false;
	    }
	  }

}
