/*
 * Created on Jan 11, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package ca.mcgill.sable.soot.cfg;

import org.eclipse.ui.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.draw2d.*;
import org.eclipse.draw2d.graph.*;
import org.eclipse.draw2d.geometry.*;
import java.util.*;

/**
 * @author jlhotak
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CFGTests  implements IWorkbenchWindowActionDelegate {

	public void run(IAction action){
		Shell shell = new Shell();
		shell.open();
		shell.setText("CFG Test");
		
		LightweightSystem lws = new LightweightSystem(shell);
		//FigureCanvas canvas = new FigureCanvas(shell, lws);
		Panel p = new Panel();
		p.setBounds(new Rectangle(0,0,-1,-1));
		lws.setContents(p);
		//IFigure label = new org.eclipse.draw2d.Label("CFG Tests");
		//canvas.add(label);
		//p.add(label);
		HashMap nodeMap = new HashMap();
		DirectedGraph dg = makeSimpleGraph();
		Iterator nIt = dg.nodes.iterator();
		while (nIt.hasNext()){
			Node nextNode = (Node)nIt.next();
			IFigure node = new RectangleFigure();
			IFigure label = new Label((String)nextNode.data);
			label.setSize(nextNode.width, 36);
			node.add(label);
			int len = ((String)nextNode.data).length() * 5;
			//node.setBounds(new Rectangle(nextNode.x, nextNode.y, -1, -1));
			node.setLocation(new Point(nextNode.x, nextNode.y));
			node.setSize(nextNode.width, 36);
			System.out.println("bounds: "+node.getBounds());
			p.add(node);
			nodeMap.put(nextNode, node);
		}
		Iterator eIt = dg.edges.iterator();
		while (eIt.hasNext()){
			Edge nextEdge = (Edge)eIt.next();
			PolylineConnection edge = new PolylineConnection();
			ChopboxAnchor ca1 = new ChopboxAnchor((IFigure)nodeMap.get(nextEdge.source));
			ChopboxAnchor ca2 = new ChopboxAnchor((IFigure)nodeMap.get(nextEdge.target));
			
			edge.setSourceAnchor(ca1);
			edge.setTargetAnchor(ca2);
			edge.setTargetDecoration(new PolygonDecoration());
			p.add(edge);
		}
		lws.setContents(p);
		Display display = Display.getDefault();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
	}
	
	public DirectedGraph makeSimpleGraph(){
		NodeList nl = new NodeList();
		Node n1 = new Node();
		String data = "y = 3";
		n1.data = data;
		n1.width = data.length() * 7;
		nl.add(n1);
		Node n2 = new Node();
		data = "if i >= 10 goto L0";
		n2.data = data;
		n2.width = data.length() * 7;
		nl.add(n2);
		Node n3 = new Node();
		data = "if i != 0 goto L1";
		n3.data = data;
		n3.width = data.length() * 7;
		nl.add(n3);
		Node n4 = new Node();
		data  = "x = 5";
		n4.data = data;
		n4.width = data.length() * 7;
		nl.add(n4);
		EdgeList el = new EdgeList();
		Edge e1 = new Edge(n1, n2);
		el.add(e1);
		Edge e2 = new Edge(n2, n3);
		el.add(e2);
		Edge e3 = new Edge(n2, n4);
		el.add(e3);
		DirectedGraph dg = new DirectedGraph();
		dg.edges = el;
		dg.nodes = nl;
		DirectedGraphLayout dgl = new DirectedGraphLayout();
		dgl.visit(dg);
		return dg;
	}
	
	public void selectionChanged(IAction action, ISelection selection){
		
	}
	
	public void dispose(){
	
	}
	
	public void init(IWorkbenchWindow window){
	
	}
	
	/**
	 * 
	 */
	public CFGTests() {
		super();
		// TODO Auto-generated constructor stub
	}

}
