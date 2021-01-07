package ccbus.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class CcbusDemoApplication
{


    public static void main(String[] args)
    {
        SpringApplication.run(CcbusDemoApplication.class, args);
    }



    public void sendMes()
    {

        //  taskScheduler.schedule()
        //	this.brokerMessagingTemplate.convertAndSend("/com/schedule/dd72ac0a-1a89-4197-927c-46e12b12b8a4","Cron rules");
    }

    @Bean
    public Executor asyncExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }
}
