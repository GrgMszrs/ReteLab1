package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;
import org.yakindu.sct.model.stext.stext.impl.EventDefinitionImpl;
import org.yakindu.sct.model.sgraph.Statechart;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		
		TreeIterator<EObject> iterator = s.eAllContents();
		List<String> variables = new ArrayList<String>(); 
		List<String> events = new ArrayList<String>(); 
		while(iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof VariableDefinition) {
				VariableDefinition variabledefinition = (VariableDefinition) content;
				variables.add(variabledefinition.getName());
				//System.out.println(variabledefinition.eClass().getName() + ":\t" + variabledefinition.getName());
			}else if(content instanceof EventDefinitionImpl) {
				EventDefinitionImpl eventdefinitionimpl = (EventDefinitionImpl) content;
				events.add(eventdefinitionimpl.getName());
				//System.out.println(eventdefinitionimpl.eClass().getName() + ":\t" + eventdefinitionimpl.getName());
			}
		}
		
		System.out.println("public class RunStatechart {\n\n" + 
				   "\tpublic static void main(String[] args) throws IOException {\n" +
				   "\t\tExampleStatemachine s = new ExampleStatemachine();\n" + 
				   "\t\ts.setTimer(new TimerService());\n" +
				   "\t\tRuntimeService.getInstance().registerStatemachine(s, 200);\n" +
				   "\t\ts.init();\n" +
				   "\t\ts.enter();\n" +
				   "\t\tBufferedReader reader = new BufferedReader(new InputStreamReader(System.in));\n" +
				   "\t\twhile(true) {\n" +
				   "\t\t\tString line = reader.readLine();");
		for(int i = 0; i < events.size(); i++) {
			if(i == 0) {
				System.out.println("\t\t\tif(line.equals(\"" + events.get(i) + "\")) {");
			}else {
				System.out.println("\t\t\t}else if(line.equals(\"" + events.get(i) + "\")) {");
			}
			String eCap = events.get(i).substring(0, 1).toUpperCase() + events.get(i).substring(1);
			System.out.println("\t\t\t\ts.raise" + eCap + "();\n" +
							   "\t\t\t\ts.runCycle();");
		}
		
		System.out.println("\t\t\t}else if(line.equals(\"" + "exit" + "\")) {\n" +
					       "\t\t\t\tprint(s);\n" +
						   "\t\t\t\tbreak;\n" + 
					       "\t\t\t}\n" +
						   "\t\t\tprint(s);\n" +
					       "\t\t}\n" +
						   "\t\treader.close();\n" +
					       "\t\tSystem.exit(0);\n" +
						   "\t}\n\n" +
					       "\tpublic static void print(IExampleStatemachine s) {");
		
		for(int i = 0; i < variables.size(); i++) {
			String vCap = variables.get(i).substring(0, 1).toUpperCase() + variables.get(i).substring(1);
			System.out.println("\t\tSystem.out.println(\"" + vCap.charAt(0) + " = \" + s.getSCInterface().get" + vCap + "());");
		}
		
		System.out.println("\t}\n" +
						   "}");
		
		/*System.out.println("public static void print(IExampleStatemachine s) {");
		for(int i = 0; i < variables.size(); i++) {
			String vCap = variables.get(i).substring(0, 1).toUpperCase() + variables.get(i).substring(1);
			System.out.println("\tSystem.out.println(\"" + vCap.charAt(0) + " = \" + s.getSCInterface().get" + vCap + "());");
		}
		System.out.println("}");*/
		
		/*int i = 0;
		while(iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				if(state.getOutgoingTransitions().size() > 0) {
					System.out.println(state.getName());
				}else {
					System.out.println("Csapda state neve:\t" + state.getName());
				}
				if(state.getName().isEmpty()) {
					boolean isContained = false;
					
					do {
						String name = "RandomName" + i;
						System.out.println(name);
						i++;
						TreeIterator<EObject> iterator2 = s.eAllContents();
						while(iterator2.hasNext()) {
							EObject content2 = iterator2.next();
							if(content2 instanceof State) {
								State state2 = (State) content2;
								if(state2.getName().equals(state.getName()) && !state2.getName().isEmpty()) {
									isContained = true;
									break;
								}
							}
						}
						state.setName(name);
					}while(isContained);
				}
				
			}else if(content instanceof Transition) {
				Transition transition = (Transition) content;
				System.out.println("Source: " + transition.getSource().getName() + "\t-->\tTarget: " + transition.getTarget().getName());
			}
		}*/
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
