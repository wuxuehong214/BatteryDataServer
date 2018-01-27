package com.green.battery.quartz;

import java.util.ArrayList;
import java.util.List;

import com.green.analyze.battery.entity.RecordEntity;
import com.green.battery.entity.CfdRecord;
import com.green.battery.entity.MaxminRecord;
import com.green.battery.entity.TaskEntity;
import com.green.battery.entity.YlwRecord;
import com.green.battery.service.CfdRecordService;
import com.green.battery.service.MaxminRecordService;
import com.green.battery.service.YlwRecordService;

/**
 * 分析进行时状态
 * 
 * @author Administrator
 * 
 */
public class AnalyzeRuntimeStatus {

	// 电压、电流、温度曲线
	private List<RecordEntity> cache = new ArrayList<RecordEntity>();
	private YlwRecordService ylwRecordService = new YlwRecordService();
	private final int FLUSH_SIZE = 100;

	// 充电记录
	private List<CfdRecord> cds;
	// 未充电记录
	private List<CfdRecord> wcds;
	// 放电记录
	private List<CfdRecord> fds;
	private CfdRecordService cfdRecordService = new CfdRecordService();
	private final int CFD_FLUSH_SIZE = 10;

	// 最大电流
	private MaxminRecord maxdl; // >200
	// 最小电流
	private MaxminRecord mindl; // <-1000
	// 最高电压
	private MaxminRecord maxdy;
	// 最低电压
	private MaxminRecord mindy;
	// 最高温度
	private MaxminRecord maxwd;
	// 最低温度
	private MaxminRecord minwd;
	private MaxminRecordService maxminRecordService = new MaxminRecordService();

	private TaskEntity task;

	public static void main(String args[]) {
	}

	public AnalyzeRuntimeStatus(TaskEntity task) {
		this.task = task;

		cds = new ArrayList<CfdRecord>();
		wcds = new ArrayList<CfdRecord>();
		fds = new ArrayList<CfdRecord>();

		maxdl = new MaxminRecord(task.getId(), MaxminRecord.TYPE_MAX_DL);
		maxdl.setValue(Integer.MIN_VALUE);
		mindl = new MaxminRecord(task.getId(), MaxminRecord.TYPE_MIN_DL);
		mindl.setValue(Integer.MAX_VALUE);
		maxdy = new MaxminRecord(task.getId(), MaxminRecord.TYPE_MAX_DY);
		maxdy.setValue(Integer.MIN_VALUE);
		mindy = new MaxminRecord(task.getId(), MaxminRecord.TYPE_MIN_DY);
		mindy.setValue(Integer.MAX_VALUE);
		maxwd = new MaxminRecord(task.getId(), MaxminRecord.TYPE_MAX_WD);
		maxwd.setValue(Integer.MIN_VALUE);
		minwd = new MaxminRecord(task.getId(), MaxminRecord.TYPE_MIN_WD);
		minwd.setValue(Integer.MAX_VALUE);

	}

	/**
	 * 新的充电记录
	 * 
	 * @param record
	 */
	public void newRecord(RecordEntity record) {
		cache.add(record);

		// 常规记录 电流、电压、温度
		if (cache.size() > FLUSH_SIZE) {
			flushRecords();
		}

		// 充电记录
		if (record.getScdl() > 200) {
			// 标记 充电开始 || 未充电结束
			cd_start(record);
			wcd_end(record);
		} else {
			// 标记充电结束 || 未充电开始
			cd_end(record);
			wcd_start(record);
		}

		// 放电记录
		if (record.getScdl() < -1000) {
			fd_start(record);
		} else {
			fd_end(record);
		}

		// 其他最值统计
		// 最大电压
		if (record.getZdy() > maxdy.getValue()) {
			maxdy.setValue(record.getZdy());
			maxdy.setStartTime(record.getDate());
			maxdy.setEndTime(record.getDate());
		} else {
			if (record.getZdy() == maxdy.getValue()) {
				maxdy.setEndTime(record.getDate());
			}
		}

		// 最低电压
		if (record.getZdy() < mindy.getValue()) {
			mindy.setStartTime(record.getDate());
			mindy.setEndTime(record.getDate());
			mindy.setValue(record.getZdy());
		} else {
			if (record.getZdy() == mindy.getValue())
				mindy.setEndTime(record.getDate());
		}

		// 最高温度
		if (record.getWd() > maxwd.getValue()) {
			maxwd.setStartTime(record.getDate());
			maxwd.setEndTime(record.getDate());
			maxwd.setValue(record.getWd());
		} else {
			if (record.getWd() == maxwd.getValue())
				maxwd.setEndTime(record.getDate());
		}

		// 最低温度
		if (record.getWd() < minwd.getValue()) {
			minwd.setStartTime(record.getDate());
			minwd.setEndTime(record.getDate());
			minwd.setValue(record.getWd());
		} else {
			if (record.getWd() == minwd.getValue()) {
				minwd.setEndTime(record.getDate());
			}
		}

	}

	/**
	 * 分析结束时检测缓存并输出
	 */
	public void finish() {
		// 温流压记录要持久化
		if (cache.size() > 0)
			flushRecords();

		// 充电、放电、未充电记录
		if (cds.size() > 0) {
			CfdRecord r = cds.get(cds.size() - 1);
			if (r.getEndTime() == null) {
				if (r.getLastSecond() != null) {
					r.setEndTime(r.getLastSecond());
					r.setContinus((int) ((r.getEndTime().getTime() - r
							.getStartTime().getTime()) / 1000));
				} else
					cds.remove(cds.size() - 1);
			}
			if (cds.size() > 0)
				cfdRecordService.addBatch(cds);
		}

		if (fds.size() > 0) {
			CfdRecord r = fds.get(fds.size() - 1);
			if (r.getEndTime() == null) {
				if (r.getLastSecond() != null) {
					r.setEndTime(r.getLastSecond());
					r.setContinus((int) ((r.getEndTime().getTime() - r
							.getStartTime().getTime()) / 1000));
				} else
					fds.remove(fds.size() - 1);
			}
			if (fds.size() > 0)
				cfdRecordService.addBatch(fds);
		}

		if (wcds.size() > 0) {
			CfdRecord r = wcds.get(wcds.size() - 1);
			if (r.getEndTime() == null) {
				if (r.getLastSecond() != null) {
					r.setEndTime(r.getLastSecond());
					r.setContinus((int) ((r.getEndTime().getTime() - r
							.getStartTime().getTime()) / 1000));
				} else
					wcds.remove(wcds.size() - 1);
			}
			if (wcds.size() > 0)
				cfdRecordService.addBatch(wcds);
		}

		// 最值记录保存
		List<MaxminRecord> records = new ArrayList<MaxminRecord>();
		maxdl.setContinus(getContinueSeconds(maxdl));
		if(maxdl.getStartTime() != null)
			records.add(maxdl);
		mindl.setContinus(getContinueSeconds(mindl));
		if(mindl.getStartTime() != null)
			records.add(mindl);
		maxdy.setContinus(getContinueSeconds(maxdy));
		if(maxdy.getStartTime() != null)
		records.add(maxdy);
		mindy.setContinus(getContinueSeconds(mindy));
		if(mindy.getStartTime() != null)
		records.add(mindy);
		maxwd.setContinus(getContinueSeconds(maxwd));
		if(maxwd.getStartTime() != null)
		records.add(maxwd);
		minwd.setContinus(getContinueSeconds(minwd));
		if(minwd.getStartTime() != null)
		records.add(minwd);
		maxminRecordService.addBatchMaxminRecord(records);
	}
	/**
	 * 获取记录的持续时间 
	 * @param record 
	 * @return   in seconds
	 */
	private int getContinueSeconds(MaxminRecord record){
		if(record.getStartTime() == null || record.getEndTime() == null) return 0;
		return (int)((record.getEndTime().getTime()-record.getStartTime().getTime())/1000);
	}

	/**
	 * 需要倾倒记录
	 */
	private void flushRecords() {
		List<YlwRecord> rs = new ArrayList<YlwRecord>();
		for (RecordEntity record : cache) {
			YlwRecord r = new YlwRecord();
			r.setDy(record.getZdy());
			r.setDl(record.getScdl());
			r.setSjc(record.getDate());
			r.setWd(record.getWd());
			r.setTaskId(task.getId());
			rs.add(r);
		}
		cache.clear();
		ylwRecordService.addBatchYlwRecord(rs);
	}

	/**
	 * 开始充电
	 */
	private void cd_start(RecordEntity record) {

		// 在适合条件下新增充电记录
		// (1) 初始化，需要新增充电记录
		// (2) 最新充电记录已经记录完成，需要新增新的充电记录
		int size = cds.size();
		if (size == 0
				|| (cds.get(size - 1).getStartTime() != null && cds.get(
						size - 1).getEndTime() != null)) {
			CfdRecord r = new CfdRecord(task.getId(), CfdRecord.TYPE_CD);
			cds.add(r);
			r.setStartTime(record.getDate()); // 记录充电开始时间
			r.setLastSecond(record.getDate());
		}
		if (size != 0) {
			CfdRecord r = cds.get(size - 1);
			if (r.getEndTime() == null && r.getStartTime() != null)
				r.setLastSecond(record.getDate());
		}

		// 如果还没有记录大值
		if (record.getScdl() > maxdl.getValue()) {
			maxdl.setStartTime(record.getDate());
			maxdl.setValue(record.getScdl());
			maxdl.setEndTime(record.getDate());
		} else {
			// 如果是持续最大值
			if (maxdl.getValue() == record.getScdl()) {
				maxdl.setEndTime(record.getDate());
			}
		}

	}

	/**
	 * 结束充电
	 * 
	 * @param record
	 */
	private void cd_end(RecordEntity record) {

		if (cds.size() > 0) {
			CfdRecord r = cds.get(cds.size() - 1);
			if (r.getStartTime() != null && r.getEndTime() == null) { // 形成充电记录
				r.setEndTime(r.getLastSecond());
				r.setContinus((int) ((r.getEndTime().getTime() - r
						.getStartTime().getTime()) / 1000));

				if (cds.size() > CFD_FLUSH_SIZE) {
					cfdRecordService.addBatch(cds);
					cds.clear();
				}
			}
		}

	}

	/**
	 * 开始放电
	 * 
	 * @param record
	 */
	private void fd_start(RecordEntity record) {

		// 放电记录
		int size = fds.size();
		if (size == 0
				|| (fds.get(size - 1).getStartTime() != null && fds.get(
						size - 1).getEndTime() != null)) {
			CfdRecord r = new CfdRecord(task.getId(), CfdRecord.TYPE_FD);
			r.setStartTime(record.getDate());
			r.setLastSecond(record.getDate());
			fds.add(r);
		}
		if (size != 0) {
			CfdRecord r = fds.get(size - 1);
			if (r.getEndTime() == null && r.getStartTime() != null)
				r.setLastSecond(record.getDate());
		}

		// 电池最大放电电流记录
		if (record.getScdl() < mindl.getValue()) {
			mindl.setStartTime(record.getDate());
			mindl.setEndTime(record.getDate());
			mindl.setValue(record.getScdl());
		} else {
			if (record.getScdl() == mindl.getValue()) {
				mindl.setEndTime(record.getDate());
			}
		}
	}

	/**
	 * 结束放电
	 * 
	 * @param record
	 */
	private void fd_end(RecordEntity record) {

		if (fds.size() > 0) {
			CfdRecord r = fds.get(fds.size() - 1);
			if (r.getStartTime() != null && r.getEndTime() == null) {
				r.setEndTime(r.getLastSecond());
				r.setContinus((int) ((r.getEndTime().getTime() - r
						.getStartTime().getTime()) / 1000));

				if (fds.size() > CFD_FLUSH_SIZE) {
					cfdRecordService.addBatch(fds);
					fds.clear();
				}
			}
		}
	}

	/**
	 * 未充电开始
	 * 
	 * @param record
	 */
	private void wcd_start(RecordEntity record) {

		int size = wcds.size();
		if (size == 0
				|| (wcds.get(size - 1).getStartTime() != null && wcds.get(
						size - 1).getEndTime() != null)) {
			CfdRecord r = new CfdRecord(task.getId(), CfdRecord.TYPE_WCD);
			r.setStartTime(record.getDate());
			r.setLastSecond(record.getDate());
			wcds.add(r);
		}
		if (size != 0) {
			CfdRecord r = wcds.get(size - 1);
			if (r.getEndTime() == null && r.getStartTime() != null)
				r.setLastSecond(record.getDate());
		}
	}

	/**
	 * 未充电结束
	 * 
	 * @param record
	 */
	private void wcd_end(RecordEntity record) {
		if (wcds.size() > 0) {
			CfdRecord r = wcds.get(wcds.size() - 1);
			if (r.getStartTime() != null && r.getEndTime() == null) {
				r.setEndTime(r.getLastSecond());
				r.setContinus((int) ((r.getEndTime().getTime() - r
						.getStartTime().getTime()) / 1000));
				if (wcds.size() > CFD_FLUSH_SIZE) {
					cfdRecordService.addBatch(wcds);
					wcds.clear();
				}
			}
		}
	}

}
