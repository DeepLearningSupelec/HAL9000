import java.util.ArrayList;

public class BackPropagation extends LearningAlgorithm {


	private void calculateNeuronDiff(NeuralNetwork N, AbstractNeuron n, Input i){

		
		
		//weightedSum is calculated for the intermediate and output neuron
		/*double weightedSum = 0;
		if(n instanceof ActiveNeuron) {
			for (Synapse s : n.getInputSynapses()) {
				weightedSum = weightedSum + s.getWeight()*s.getInputNeuron().getOutput() ;
			}
			weightedSum = weightedSum - n.bias;

		}*/

		
		
		
		// The operations differ whether the neuron is an OutputNeuron or another neuron
		
		if (n instanceof OutputNeuron ){
			
			double error = i.expectedOutput()[N.outputNeurons.indexOf(n)] - n.output;
			n.setNeuronDiff(n.activationFunction.applyDerivative(((ActiveNeuron)n).getIntermediateValue())*error); // delta = f'(input)*e

		}

		if (n instanceof IntermediateNeuron) {
			//weighted sum of the output is calculated
			double weightedErrorOutput = 0 ;
			for (Synapse s : n.getOutputSynapses()) {
				weightedErrorOutput = weightedErrorOutput +s.getWeight()*s.getOutputNeuron().getNeuronDiff() ;
			}
			n.setNeuronDiff (n.activationFunction.applyDerivative(((ActiveNeuron)n).getIntermediateValue())*weightedErrorOutput); // delta = f'(input)* sum ( gradient next Neuron * weight linked synapse) 
		}

	}



	
	
	
	
	private void incrementWeightsDiff(Synapse s){
		s.setWeightDiff(s.getInputNeuron().getOutput()*s.getOutputNeuron().getNeuronDiff());
		
	}

	private void incrementBiasDiff(ActiveNeuron n){
		n.setBiasDiff(n.getNeuronDiff());
	}
	
	private void incrementBias(ActiveNeuron n, double a){
		n.setBias(n.getBias() + a*n.getBiasDiff());
	}

	private void incrementWeights(Synapse s, double a ){
		s.setWeight(s.getWeight() + a*s.getWeightDiff());
	}

	
	

	public void launch(NeuralNetwork N, double a, Input I){ //BackPropagation : learning algorithm; a is the learning rate

		if (N instanceof Perceptron){

			for (AbstractNeuron n : N.outputNeurons ){ //Beginning with the end of the Neural Network

				if(n instanceof OutputNeuron){  // all NeuronDiff are calculated

					calculateNeuronDiff(N, n , I);
					incrementBiasDiff((ActiveNeuron) n);
					
					for(Synapse s : n.getInputSynapses()){ // Weight gradient are updated  between the output layer and the hidden layer
						incrementWeightsDiff(s);
					}
				}
			}



			for(int i=((Perceptron) N).layers.length - 1  ; i==0 ; i=i-1){ 	// Layer after layer, from the end to the beginning

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
