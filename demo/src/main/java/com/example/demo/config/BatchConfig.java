package com.example.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private PersonRepository repo;
	
	@Value("classpath:input/inputData*.csv")
	private Resource[] inputResources;
	
	
	
		
    @Bean
    public FlatFileItemReader<Person> reader() {
        FlatFileItemReader<Person> itemReader = new FlatFileItemReader<Person>();
        //itemReader.setResource(new FileSystemResource("src/main/resources/input.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Person> lineMapper() {
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "name", "salary", "email", "gender", "contactNo", "country", "dob","college");

        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }
    
    
    
    @Bean
    public MultiResourceItemReader<Person> multiResourceItemReader() 
    {
      MultiResourceItemReader<Person> resourceItemReader = new MultiResourceItemReader<Person>();
      resourceItemReader.setResources(inputResources);

      resourceItemReader.setDelegate(reader());
      return resourceItemReader;
    }
	
	@Bean
	public Processor processor() {
		return new Processor();
	}
	
	@Bean
	public RepositoryItemWriter<Person> writer(){
		RepositoryItemWriter<Person> writer=new RepositoryItemWriter<>();
		writer.setRepository(repo);
		writer.setMethodName("save");
		return writer;
		
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("csv-step").<Person,Person>chunk(10)
	               .reader(multiResourceItemReader())
	                .processor(processor())
	                .writer(writer())
	                .taskExecutor(taskExecutor())
	                .build();
	    }
	  
	@Bean
	public Job runJob() {
		return jobBuilderFactory.get("importPerson")
				.flow(step1()).end().build();
	}
	
	@Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }
}
