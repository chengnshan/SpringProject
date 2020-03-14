insert into quartz_task_info (id,job_name,job_group,job_description,job_class_name,job_status,cron_expression,create_time)
values (1,'myJob','myJob','myJob','myJob','1','0/1 * * * * ?',now());

insert into quartz_task_info (id,job_name,job_group,job_description,job_class_name,job_status,cron_expression,create_time)
values (3,'myJobThree','myJobThree','myJobThree','myJobThree','0','* 0/5 * * * ?',now());

