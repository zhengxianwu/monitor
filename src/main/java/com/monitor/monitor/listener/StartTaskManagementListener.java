package com.monitor.monitor.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.monitor.monitor.schedule.SpringDynamicCronTask;
import com.monitor.monitor.schedule.TaskManagement;


//监听器，用于容器未完成启动时调用方法
//启动开始 ---> 监听器  -----> 容器加载完成
//在监听器启动TaskManagement;
@Service
public class StartTaskManagementListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private TaskManagement taskManage;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		if(event.getApplicationContext().getParent() == null) {
			taskManage.init();
			System.out.println(taskManage);
			List<SpringDynamicCronTask> taskList = taskManage.getTaskList();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					taskList.get(0).stopTask();
				}
			}).start();
		}
	}

}
