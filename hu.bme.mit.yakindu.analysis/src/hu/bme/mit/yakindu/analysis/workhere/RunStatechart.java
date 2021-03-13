package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.stext.stext.VariableDefinition;
import org.yakindu.sct.model.stext.stext.impl.EventDefinitionImpl;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

/*public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		while(true) {
			String line = reader.readLine();
			if(line.equals("start")) {
				s.raiseStart();
				s.runCycle();
			}else if(line.equals("white")) {
				s.raiseWhite();
				s.runCycle();
			}else if(line.equals("black")) {
				s.raiseBlack();
				s.runCycle();
			}else if(line.equals("exit")) {
				print(s);
				break;
			}
			print(s);
		}
		reader.close();
		System.exit(0);
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
}*/

public class RunStatechart {

	public static void main(String[] args) throws IOException {		
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			String line = reader.readLine();
			if(line.equals("start")) {
				s.raiseStart();
				s.runCycle();
			}else if(line.equals("white")) {
				s.raiseWhite();
				s.runCycle();
			}else if(line.equals("black")) {
				s.raiseBlack();
				s.runCycle();
			}else if(line.equals("exit")) {
				print(s);
				break;
			}
			print(s);
		}
		reader.close();
		System.exit(0);
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
}