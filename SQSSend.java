import java.util.Date;
import java.util.Scanner;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SQSSend {

	//private static final String QUEUE_NAME = "testQueue" + new Date().getTime();
	
	private static final String QUEUE_NAME = "SampleQueue";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

		try {
			CreateQueueResult create_result = sqs.createQueue(QUEUE_NAME);
		} catch (AmazonSQSException e) {
			if (!e.getErrorCode().equals("QueueAlreadyExists")) {
				throw e;
			}
		}

		String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
		Scanner sc=new Scanner(System.in);
		String str="";


		while(!(str=sc.next()).equals("end")) {
			SendMessageRequest send_msg_request = new SendMessageRequest()
					.withQueueUrl(queueUrl)
					.withMessageBody(str)
					.withDelaySeconds(5);
			sqs.sendMessage(send_msg_request);
		}

	}

}
