package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.sgraph.Entry;
import org.yakindu.sct.model.sgraph.Region;
import org.yakindu.sct.model.sgraph.Statechart;

import hu.bme.mit.model2gml.Model2GML;
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
		//TreeIterator<EObject> iterator = s.eAllContents();
		//while (iterator.hasNext()) {
		//	EObject content = iterator.next();
		//	if(content instanceof State) {
		//		State state = (State) content;
		//		System.out.println(state.getName());
		//	}
			//else if(content instanceof Transition) {
			//	Transition transition = (Transition) content;
			//	System.out.println(transition.toString());
			//}
		
		//}
		/*if(content instanceof State) {
			State state = (State) content;
			System.out.println(i + "\tState: " + state.getName());
		}else if(content instanceof Transition){
			Transition transition = (Transition) content;
			System.out.println(i + "\tTransition: " + transition.getSpecification());
		}else if(content instanceof Entry){
			Entry entry = (Entry) content;
			System.out.println(i + "\tEntry: " + entry.getName());
		}else if(content instanceof Region){
			Region region = (Region) content;
			System.out.println(i + "\tRegion: " + region.getName());
		}*/
		TreeIterator<EObject> iterator = s.eAllContents();
		int i = 0;
		EObject content2 = null;
		EObject content1 = null; 
		while (iterator.hasNext()) {
			 if(i == 0) {
				content1 = iterator.next();
			}else {
				if(content2 instanceof State) {
					content1 = content2;
				}
			}
			content2 = iterator.next();
			
			if(content1 instanceof State && content2 instanceof State) {
				State state = (State) content1;
				State state2 = (State) content2; 
				System.out.println(state.getName() + "\t-->\t" + state2.getName());
			}
			
			/*
			if(content.eClass().getName() == "Transition") {
				System.out.println("Start: " + content.eClass().getName() + "\t-->\tEnd: " + content2.eClass().getName());
			}else {
				System.out.println("Start: " + content.eClass().getName() + "\t\t-->\tEnd: " + content2.eClass().getName());
			}*/
			/*
			if(content instanceof State) {
				State state = (State) content;
				System.out.println(i + "\tState: " + state.getName());
			}else if(content instanceof Transition){
				Transition transition = (Transition) content;
				System.out.println(i + "\tTransition: " + transition.getSpecification());
			}else if(content instanceof Entry){
				Entry entry = (Entry) content;
				System.out.println(i + "\tEntry: " + entry.getName());
			}else if(content instanceof Region){
				Region region = (Region) content;
				System.out.println(i + "\tRegion: " + region.getName());
			}
			if(content2 instanceof State) {
				State state = (State) content2;
				System.out.println(i + "\tState: " + state.getName());
			}else if(content2 instanceof Transition){
				Transition transition = (Transition) content2;
				System.out.println(i + "\tTransition: " + transition.getSpecification());
			}else if(content2 instanceof Entry){
				Entry entry = (Entry) content2;
				System.out.println(i + "\tEntry: " + entry.getName());
			}else if(content2 instanceof Region){
				Region region = (Region) content2;
				System.out.println(i + "\tRegion: " + region.getName());
			}*/
			i++;
		}
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
