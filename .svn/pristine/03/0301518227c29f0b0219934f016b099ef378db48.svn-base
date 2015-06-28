package com.moodykettle.unittests.file.xml;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlDOMParser {

	/**
	 * I take an employee element and read the values in, create
	 * an Employee object and return it
	 */
	public Employee getEmployee(Element empEl) {

		//for each <employee> element get text or int values of
		//name ,id, age and name
		String name = getTextValue(empEl,"Name");
		int id = getIntValue(empEl,"Id");
		int age = getIntValue(empEl,"Age");
		String type = empEl.getAttribute("type");

		//Create a new Employee with the value read from the xml nodes
		Employee e = new Employee(name,id,age,type);

		return e;
	}


	public void parseDocument(Document dom, Vector<Employee> myEmpls){
		//get the root element
		Element docEle = dom.getDocumentElement();

		//get a nodelist of  elements
		NodeList nl = docEle.getElementsByTagName("Employee");		
		if(nl != null && nl.getLength() > 0) 
		{
			for(int i = 0 ; i < nl.getLength();i++)
			{
				//get the employee element
				Element el = (Element)nl.item(i);

				//get the Employee object
				Employee e = getEmployee(el);

				//add it to list
				myEmpls.add(e);
			}
		}
	}

	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is 'name' I will return John
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) 
		{
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}


	/**
	 * Calls getTextValue and returns a int value
	 */
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}

	
	public void printData(Vector<Employee> myEmpls){

		System.out.println("No of Employees '" + myEmpls.size() + "'.");

		Iterator<Employee> it = myEmpls.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("Input xml file: ");
			String str = sc.nextLine();
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			Document dom = db.parse(str);

			XmlDOMParser p = new XmlDOMParser();
			Vector<Employee> myEmpls = new Vector<Employee>();
			p.parseDocument(dom, myEmpls);
			p.printData(myEmpls);			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
