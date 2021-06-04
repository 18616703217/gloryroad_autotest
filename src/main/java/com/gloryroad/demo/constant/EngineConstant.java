package com.gloryroad.demo.constant;

public class EngineConstant {
    /** 正在运行的任务个数 */
    public static final String TASK_RUNNING_COUNT_KEY  = "TaskRunningCount";

    /** 报告信息URL*/
    public static final String REPORT_HOST="";

    public static final String REPORT_URI="";

    /** 运行任务边界值 */
    public static final int TASK_RUN_COUNT_BORDER = 30;

    /** 任务执行队列Redis Key */
    public static final String TASK_RUN_QUEUE_ZSET  = "task_run_queue";

    /** 任务执行内容和任务id映射 */
    public static final String TASK_RUN_HASH  = "task_run_hash";




}
