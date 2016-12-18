package model.util.worldgen;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import model.GameBoard;

/**
 * Contains methods that can generate dynamic game content
 * configured in layout.xml
 * @author Brian Lovelace
 */
public class WorldGen implements Serializable
{
	public static final String FILENAME1 = "./assets/layout.xml";
	public static final String ROOT_TAG = "WorldDef";
	public static final String LAYOUT_TAG = "LayoutDef";
	public static final String DESCDEF_TAG = "DescriptionDef";
	
	private LayoutGenerator layoutGen;
	
	private WorldGen(LayoutGenerator layoutGen)
	{
		this.layoutGen = layoutGen;
	}
	
	/**
	 * Returns the GameBoard associated with WorldGen
	 * @return
	 */
	public GameBoard generateGameBoard()
	{
		return new GameBoard(layoutGen.getRooms());
	}
	
	/**
	 * Returns a WorldGen object which is tailored to
	 * the World defined in layout.xml. 
	 * @return new WorldGen object
	 */
	public static WorldGen getWorldGen(String filename)
	{
		LayoutGenerator layoutGen = null;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setValidating(true);
	    factory.setIgnoringElementContentWhitespace(true);
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        File file = new File(filename);
	        Document doc = builder.parse(file);
	               
	        Element e = doc.getDocumentElement();
	        Node layoutDef = e.getFirstChild();
	        Node entityDef = layoutDef.getNextSibling();
	        layoutGen = new LayoutGenerator(layoutDef, entityDef);
        
	        // Do something with the document here.
	    } catch (ParserConfigurationException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (SAXException e)
		{
	    	e.printStackTrace();
		} 
	    
		WorldGen worldgen = new WorldGen(layoutGen);
		return worldgen;
	}
		
	public static void main(String[] args)
	{
		WorldGen gen = getWorldGen(FILENAME1);
		GameBoard board = gen.generateGameBoard();
		System.out.println(board);
	}
}
