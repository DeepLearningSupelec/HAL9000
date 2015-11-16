import java.util.ArrayList;

public class BackPropagation extends LearningAlgorithm {

	final double momentumFactor = 0.00;
	
	

	private void calculateNeuronDiff(NeuralNetwork N, AbstractNeuron n, Input i){

		
		
	
		// The operations differ whether the neuron is an OutputNeuron or another neuron
		
		if (n instanceof OutputNeuron ){
			
			double error = i.expectedOutput()[N.outputNeurons.indexOf(n)] - n.output;
			//System.out.println(error);
			n.setNeuronDiff(n.activationFunction.applyDerivative(((ActiveNeuron)n).getIntermediateValue())*error); // delta = f'(input)*e
			//System.out.println(n.activationFunction.applyDerivative(((ActiveNeuron)n).getIntermediateValue())*error);
		}

		if (n instanceof IntermediateNeuron) {
			//weighted sum of the output is calculated
			double weightedErrorOutput = 0 ;
			for (Synapse s : n.getOutputSynapses()) {
				weightedErrorOutput = weightedErrorOutput +s.getWeight()*s.getOutputNeuron().getNeuronDiff() ;
			}
			//System.out.println(weightedErrorOutput);
			n.setNeuronDiff (n.activationFunction.applyDerivative(((ActiveNeuron)n).getIntermediateValue())*weightedErrorOutput); // delta = f'(input)* sum ( gradient next Neuron * weight linked synapse) 
			//System.out.println("weightederroroutput: " + weightedErrorOutput);
		}
		

		//System.out.println("neuron diff: " + ((ActiveNeuron)n).getNeuronDiff());
		//System.out.println("intermediate value: " + ((ActiveNeuron)n).getIntermediateValue());
	}



	
	
	
	
	private void incrementWeightsDiff(Synapse s){
		s.setWeightDiff(s.getWeightDiff()*momentumFactor + s.getInputNeuron().getOutput()*s.getOutputNeuron().getNeuronDiff());
		
	}

	private void incrementBiasDiff(ActiveNeuron n){
		n.setBiasDiff(n.getBiasDiff()*momentumFactor + n.getNeuronDiff());
	}
	
	private void incrementBias(ActiveNeuron n, double a){
		n.setBias(n.getBias() + a*n.getBiasDiff());
	}

	private void incrementWeights(Synapse s, double a ){
		s.setWeight(s.getWeight() + a*s.getWeightDiff());
	}

	
	

	public void launch(NeuralNetwork N, double a, Input I){ //BackPropagation : learning algorithm; a is the learning rate

		if (N instanceof Perceptron){

			/*for (AbstractNeuron n : N.outputNeurons ){ //Beginning with the end of the Neural Network

				if(n instanceof OutputNeuron){  // all NeuronDiff are calculated

					calculateNeuronDiff(N, n , I);
					incrementBiasDiff((ActiveNeuron) n);
					
					for(Synapse s : n.getInputSynapses()){ // Weight gradient are updated  between the output layer and the hidden layer
						incrementWeightsDiff(s);
						//System.out.println(" Weightdiff :" + s.getWeightDiff());
					}
				}
			}*/


			for(int i=((Perceptron) N).layers.length - 1  ; i>=0 ; i=i-1){ 	// Layer after layer, from the end to the beginning

				
				for(AbstractNeuron n : ((Perceptron) N).layers[i] ){	// inside the layer

					if(n instanceof ActiveNeuron ){ //NeuronDiff are calculated
						calculateNeuronDiff(N, n, I);
						incrementBiasDiff((ActiveNeuron) n);

						for(Synapse s : n.getInputSynapses()){ // Weight gradient are updated between the input layers and the hidden layers
							incrementWeightsDiff(s);
						}
					}
				}
			}


			for(AbstractNeuron n : N.outputNeurons ){   //Synapse's weight are updated
				incrementBias((ActiveNeuron) n, a);
				for(Synapse s : n.getInputSynapses()){
					incrementWeights(s,a);
				}
			}

			for(AbstractNeuron n : N.intermediateNeurons ){ //Synapse's weight are updated
				incrementBias((ActiveNeuron) n, a);
				for(Synapse s : n.getInputSynapses()){
					incrementWeights(s,a);
				}
			}
		}
	}


}
