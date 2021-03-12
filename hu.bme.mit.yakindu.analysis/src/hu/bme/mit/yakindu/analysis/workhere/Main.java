package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;
import org.yakindu.sct.model.stext.stext.impl.EventDefinitionImpl;
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
		
		TreeIterator<EObject> iterator = s.eAllContents();
		while(iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof VariableDefinition) {
				VariableDefinition variabledefinition = (VariableDefinition) content;
				System.out.println(variabledefinition.eClass().getName() + ":\t" + variabledefinition.getName());
			}else if(content instanceof EventDefinitionImpl) {
				EventDefinitionImpl eventdefinitionimpl = (EventDefinitionImpl) content;
				System.out.println(eventdefinitionimpl.eClass().getName() + ":\t" + eventdefinitionimpl.getName());
			}
		}
		
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
