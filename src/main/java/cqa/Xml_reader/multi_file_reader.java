package cqa.Xml_reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class multi_file_reader 
{
	static ArrayList<Integer> length_pot = new ArrayList<>();
	static ArrayList<Integer> length_pot_dev = new ArrayList<>();
	static ArrayList<Integer> length_auth = new ArrayList<>();
	static ArrayList<Integer> length_auth_dev = new ArrayList<>();
	public static void main(String args[])
	{
		String SVM_dir = args[0];
		String[] SVM_files_train = {SVM_dir+"S_string.txt", SVM_dir+"S_thread.txt", SVM_dir+"S_topic.txt", SVM_dir+"S_embedding.txt"};
		String[] SVM_files_dev = {SVM_dir+"S_string_dev.txt", SVM_dir+"S_thread_dev.txt", SVM_dir+"S_topic_dev.txt", SVM_dir+"S_embedding_dev.txt"};
		String RankLib_dir = args[1];
		String[] RankLib_files_train = {RankLib_dir+"R_string.txt", RankLib_dir+"R_thread.txt", RankLib_dir+"R_topic.txt", RankLib_dir+"R_embedding.txt"};
		String[] RankLib_files_dev = {RankLib_dir+"R_string_dev.txt", RankLib_dir+"R_thread_dev.txt", RankLib_dir+"R_topic_dev.txt", RankLib_dir+"R_embedding_dev.txt"};
		len_cal(args[2], args[3], 0);
		//multireader(RankLib_files_train, RankLib_dir+"R_total.txt", 0, 231, 0, 0);
		multireader(SVM_files_train, SVM_dir+"S_total.txt",1, 153, 0, 0);
		//multireader(RankLib_files_dev, RankLib_dir+"R_total_dev.txt", 0, 231, 0, 1);
		multireader(SVM_files_dev, SVM_dir+"S_total_dev.txt",1, 153, 0, 1);
	}
	public static void multireader(String[] input_dir, String output_file, int flag, int n, int flag1, int flag2)          //combine all data files and normalize
	{
		ArrayList<Integer> l;
		if(flag1 == 0)
		{
			if(flag2 == 0)
			{
				l = length_auth;
			}
			else
			{
				l = length_auth_dev;
			}
		}
		else
		{
			if(flag2 == 0)
			{
				l = length_pot;
			}
			else
			{
				l = length_pot_dev;
			}
		}
		BufferedReader[] reader = new BufferedReader[4];
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(output_file, false)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=0; i<4; i++)
		{
			File file = new File(input_dir[i]);
			try {
				reader[i] = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int caller = 0;
		double[][] arr = new double[n][l.get(0)]; 
		int[] labels = new int[l.get(0)];
		String line;
		int breaker = 0;
		int q_id_rank = 0;
		try {
			while(true)
			{
				caller++;
				int count = 0;
				int start;
				for(int i=0; i<4; i++)
				{
					line = reader[i].readLine();
					if(line == null)
					{
						breaker = 1;
						break;
					}
					String[] splited = line.split("\\s+");
					if(flag == 0)
					{
						start = 2;
						for(int j=start; j<splited.length-2; j++)
						{
								//System.out.println(splited[j]);
							if(j<11)
							{
								arr[count][caller-1] = Double.parseDouble(splited[j].substring(2));
							}
							else if(j < 101)
							{
								arr[count][caller-1] = Double.parseDouble(splited[j].substring(3));
							}
							else
							{
								arr[count][caller-1] = Double.parseDouble(splited[j].substring(4));
							}
							count++;
						}
					}
					else
					{
						start = 1;
						for(int j=start; j<splited.length; j++)
						{
							//System.out.println(splited[j]);
							if(j<10)
							{
								arr[count][caller-1] = Double.parseDouble(splited[j].substring(2));
							}
							else if(j < 100)
							{
								arr[count][caller-1] = Double.parseDouble(splited[j].substring(3));
							}
							else
							{
								arr[count][caller-1] = Double.parseDouble(splited[j].substring(4));
							}
							count++;
						}
					}	
					if(i==0)
					{
						labels[caller-1] = Integer.parseInt(splited[0]);
					}
					
				}
				if(caller == l.get(q_id_rank))
				{
					normalize(arr, n, l.get(q_id_rank));
					q_id_rank++;
					for(int i=0; i<l.get(q_id_rank-1); i++)
					{
						if(flag == 0)
						{
							writer.print(labels[i]+" qid:"+q_id_rank+" ");
						}
						else
							writer.print(labels[i]+" ");
						for(int j=0; j<n; j++)
						{
							writer.print((j+1)+":"+arr[j][i]+" ");
						}
						writer.println();
					}
					
					if(q_id_rank == l.size())
					{
						break;
					}
					arr = new double[n][l.get(q_id_rank)];
					labels = new int[l.get(q_id_rank)];
					caller = 0;
				}
				if(breaker == 1)
				{
					break;
				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void len_cal(String input1, String input2, int flag)
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader(input1));
			BufferedReader reader2 = new BufferedReader(new FileReader(input2));
			String line;
			try {
				while((line = reader.readLine()) != null)
				{
					String[] qs = line.split("\\s+");
					int num = Integer.parseInt(qs[1]);
					if(flag == 0)
					{
						length_auth.add(num);
					}
					else
					{
						length_pot.add(num);
					}
					reader.readLine();
					for(int i=0; i<num; i++)
					{
						reader.readLine();
						reader.readLine();
					}
				}
				while((line = reader2.readLine()) != null)
				{
					String[] qs = line.split("\\s+");
					int num = Integer.parseInt(qs[1]);
					if(flag == 0)
					{
						length_auth_dev.add(num);
					}
					else
					{
						length_pot_dev.add(num);
					}
					reader2.readLine();
					for(int i=0; i<num; i++)
					{
						reader2.readLine();
						reader2.readLine();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(length_auth.size()+" "+length_auth_dev.size());
	}
	public static void normalize(double[][] arr, int n, int len)                   //normalization code
	{
		for(int i=0; i<n; i++)
		{
			double[] data = new double[len];
			for(int j=0; j<len; j++)
			{
				data[j] = arr[i][j];
			}
			Statistics a = new Statistics(data);
			if(a.getStdDev() != 0.0)
			{
				for(int j=0; j<len; j++)
				{
					
						arr[i][j] = (arr[i][j] - a.getMean())/a.getStdDev();
				}
			}
			
//			double max = Collections.max(d);
//			double min = Collections.min(d);
//			for(int j=0; j<10; j++)
//			{
//				if((max - min)!=0)
//					arr[i][j] = (arr[i][j] - min)/ (max - min);
//			}
		}
	}
}

class Statistics 
{
    double[] data;
    int size;   

    public Statistics(double[] data) 
    {
        this.data = data;
        size = data.length;
    }   

    double getMean()
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }

    double getVariance()
    {
        double mean = getMean();
        double temp = 0;
        for(double a :data)
            temp += (mean-a)*(mean-a);
        return temp/size;
    }

    double getStdDev()
    {
        return Math.sqrt(getVariance());
    }
}
