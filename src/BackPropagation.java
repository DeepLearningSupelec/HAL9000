import java.util.ArrayList;

public class BackPropagation extends LearningAlgorithm {


	public int[] expectedOutput(Input i){
		int[] expectedOutput = new int[10] ;
		for (int j=0 ; j<i.getLabel() ; j=j+1 ){
			expectedOutput[j] = 0;
		}
		expectedOutput [i.getLabel()] = 1;
		for (int j=i.getLabel()+1 ; j<=9 ; j=j+1 ){
			expectedOutput[j] = 0;
		}
		return expectedOutput;
	}
	
	private void calculateNeuronDiff(NeuralNetwork N, AbstractNeuron n, Input i){

		
		
		//weightedSum is calculated for the intermediate and output neuron
		double weightedSum = 0;
		if(n instanceof ActiveNeuron) {
			for (Synapse s : n.getInputSynapses()) {
				weightedSum = weightedSum + s.getWeight()*s.getInputNeuron().getOutput() ;
			}
			weightedSum = weightedSum - n.bias;

		}

		// The operations differ whether the neuron is an OutputNeuron or another neuron
		
		if (n instanceof OutputNeuron ){
			;
			double error = expectedOutput(i)[N.outputNeurons.indexOf(n)] - n.output;
			n.setNeuronDiff(n.activationFunction.applyDerivative(weightedSum)*error); // delta = f'(input)*e

		}

		if (n instanceof IntermediateNeuron) {
			//weighted sum of the output is calculated
			double weightedErrorOutput = 0 ;
			for (Synapse s : n.getOutputSynapses()) {
				weightedErrorOutput = weightedErrorOutput +s.getWeight()*s.getOutputNeuron().getNeuronDiff() ;
			}
			n.setNeuronDiff (n.activationFunction.applyDerivative(weightedSum)*weightedErrorOutput); // delta = f'(input)* sum ( gradient next Neuron * weight linked synapse) 
		}

	}



	private void incrementWeightsDiff(Synapse s){
		if(s.getOutputNeuron() instanceof OutputNeuron){
			s.setWeightDiff(s.getWeightDiff()+s.getInputNeuron().getOutput()*s.getOutputNeuron().getNeuronDiff());
		}
		else{
			s.setWeightDiff(s.getWeightDiff()+s.getInputNeuron().getOutput()*s.getOutputNeuron().getNeuronDiff());
		}
	}



	private void incrementWeights(Synapse s, double a ){
		s.setWeight(s.getWeight()+ a*s.getWeightDiff());
	}


	public void launch(NeuralNetwork N, double a, Input I){ //BackPropagation : learning algorithm; a is the learning rate

		if (N instanceof Perceptron){

			for (AbstractNeuron n : N.outputNeurons ){ //Beginning with the end of the Neural Network

				if(n instanceof OutputNeuron){  // all NeuronDiff are calculated

					calculateNeuronDiff(N, n , I);

					for(Synapse s : n.getInputSynapses()){ // Weight gradient are updated  between the output layer and the hidden layer
						incrementWeightsDiff(s);
					}
				}
			}



			for(int i=((Perceptron) N).layers.length ; i==1 ; i=i-1){ 	// Layer after layer, from the end to the beginning

				for(AbstractNeuron n : ((Perceptron) N).layers[i] ){	// inside the layer

					if(n instanceof IntermediateNeuron ){ //NeuronDiff are calculated
						calculateNeuronDiff(N, n, I);

						for(Synapse s : n.getInputSynapses()){ // Weight gradient are updated between the input layers and the hidden layers
							incrementWeightsDiff(s);
						}
					}
				}
			}


			for(AbstractNeuron n : N.outputNeurons ){   //Synapse's weight are updated
				for(Synapse s : n.getInputSynapses()){
					incrementWeights(s,a);
				}
			}

			for(AbstractNeuron n : N.intermediateNeurons ){ //Synapse's weight are updated
				for(Synapse s : n.getInputSynapses()){
					incrementWeights(s,a);
				}
			}
		}
	}


}
