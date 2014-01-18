package org.colossus.destalarm.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.colossus.destalarm.model.Destination;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.android.gms.maps.model.LatLng;

public class Util {
	private static final String GEOCODER_REQUEST_PREFIX_FOR_XML = "http://maps.google.com/maps/api/geocode/xml";
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isStringBlank(String str) {
		return (str == null) || ("".equals(str.trim()));
	}
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isListBlank(List<?> list) {
		return (list == null) || (list.isEmpty());
	}
	
	@SuppressWarnings("finally")
	/**
	 * 
	 * @param address
	 * @return
	 */
	public static List<Destination> getLocationInfo(String address) {
		List<Destination> locationList = new ArrayList<Destination>();
		HttpURLConnection conn = null;
		Document geocoderResultDocument = null;

		try {
			URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?address="
					+ URLEncoder.encode(address, "UTF-8") + "&sensor=false");

			conn = (HttpURLConnection) url.openConnection();

			// open the connection and get results as InputSource.
			conn.connect();
			InputSource geocoderResultInputSource = new InputSource(
					conn.getInputStream());

			// read result and parse into XML Document
			geocoderResultDocument = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(geocoderResultInputSource);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			conn.disconnect();
		}
		try {
			// prepare XPath
			XPath xpath = XPathFactory.newInstance().newXPath();

			// extract the result
			NodeList resultNodeList = null;
			
			String resultExp = "/GeocodeResponse/result";
			
			resultNodeList = (NodeList) xpath.evaluate(
					resultExp,
					geocoderResultDocument, XPathConstants.NODESET);
			int resultNodeListLen = resultNodeList.getLength();
			
			for (int i = 0; i < resultNodeListLen; i++) {
				
				

				Node tmpNode = resultNodeList.item(i);

				String tmpAddressExp = "./formatted_address";
				Node tmpAddressNode = (Node) xpath.evaluate(tmpAddressExp, tmpNode, XPathConstants.NODE);
				String tmpAddressNodeTextContent = tmpAddressNode.getTextContent();
				
				
				
				
				double lat = Double.NaN;
				double lng = Double.NaN;
				
				
				String tmpGeometryExp = "./geometry/location/*";
				NodeList tmpGeometryNodeList = (NodeList) xpath.evaluate(
						tmpGeometryExp,
						tmpNode, XPathConstants.NODESET);
				
				int tmpGeometryNodeListLen = tmpGeometryNodeList.getLength();
				for (int j = 0; j < tmpGeometryNodeListLen; j++) {
					Node tmpGeometryNode = tmpGeometryNodeList.item(j);
					String tmpGeometryNodeName = tmpGeometryNode.getNodeName();
					String tmpGeometryNodeTextContent = tmpGeometryNode.getTextContent();
					if ("lat".equals(tmpGeometryNodeName)){
						lat = Float.parseFloat(tmpGeometryNodeTextContent);
					}else if ("lng".equals(tmpGeometryNodeName)){
						lng = Float.parseFloat(tmpGeometryNodeTextContent);
					}
				}
				
				locationList.add(new Destination(new LatLng(lat,lng),tmpAddressNodeTextContent));
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return locationList;
		}

	}
	

}
