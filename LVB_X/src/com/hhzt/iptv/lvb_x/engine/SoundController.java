/**
 * 作者：   Johnson
 * 日期：   2014年6月11日下午2:35:33
 * 包名：    com.hhzt.iptv.lvb_x.engine
 * 工程名：LVB_X
 * 文件名：SoundController.java
 */
package com.hhzt.iptv.lvb_x.engine;

import java.util.HashMap;

import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.log.LogUtil;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundController {

	private AudioManager audioManager;
	private int streamVolumeMax;
	private int streamVolumeCurrent;
	private SoundPool soundPool;
	private MediaPlayer mediaPlayerBack;
	private HashMap<Integer, Integer> soundPoolMapId;

	// soundpool最多同事可以播放的音频数量
	public final int SND_COUNT_MAX = 10;

	private int soundId = 0;

	private boolean isSilent;

	public SoundController(Application app) {
		initSoundController(app);
	}

	public void initSoundController(Application app) {
		audioManager = (AudioManager) app.getSystemService(Context.AUDIO_SERVICE);
		streamVolumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		streamVolumeCurrent = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(SND_COUNT_MAX, AudioManager.STREAM_MUSIC, 100);
		mediaPlayerBack = new MediaPlayer();
		isSilent = false;
		initSoundPool();
	}

	/**
	 * 所有音频文件组织装在hashmap中
	 */
	public void initSoundPool() {
		// soundPoolMapId=new HashMap<Integer, Integer>();
		// soundPoolMapId.put(SND_LANDLORD_ROOM2_BG,soundPool.load(LandlordMainActivity.getInstance(),
		// R.raw.audio_effect_game_bg2, 1));
	}

	/**
	 * 播放背景音通用接口
	 * 
	 * @param soundBId
	 */
	public void playMediaPlaySoundBg(Application app, int soundBId) {
		boolean isLoop = true;
		if (null != mediaPlayerBack) {
			if (true == mediaPlayerBack.isPlaying()) {
				mediaPlayerBack.stop();
			}
			mediaPlayerBack.release();
			mediaPlayerBack = null;
		}

		mediaPlayerBack = MediaPlayer.create(app, soundBId);
		mediaPlayerBack.setVolume(streamVolumeMax / 5, streamVolumeMax / 5);
		mediaPlayerBack.setLooping(isLoop);
		mediaPlayerBack.start();
	}

	/**
	 * 暂停MediaPlay的播放
	 */
	public void pauseMediaPlay() {
		if (mediaPlayerBack != null) {
			if (mediaPlayerBack.isPlaying()) {
				mediaPlayerBack.pause();
			}
		}
	}

	/**
	 * 恢复MediaPlay的播放
	 */
	public void resumeMediaPlay() {
		if (mediaPlayerBack != null) {
			if (!mediaPlayerBack.isPlaying()) {
				mediaPlayerBack.start();
			}
		}
	}

	/**
	 * 停止MediaPlay声音
	 */
	public void stopMediaPlayBgSound() {
		if (null != mediaPlayerBack) {
			if (mediaPlayerBack.isPlaying()) {
				mediaPlayerBack.stop();
			}
			mediaPlayerBack.release();
			mediaPlayerBack = null;
		}
	}

	/**
	 * 暂停SoundPool音效的播放
	 */
	public void pauseSoundPool() {
		if (soundPool != null) {
			if (soundId != 0) {
				soundPool.pause(soundId);
			}
		}
	}

	/**
	 * 恢复SoundPool音效的播放
	 */
	public void resumeSoundPool() {
		if (soundPool != null) {
			if (soundId != 0) {
				soundPool.resume(soundId);
			}
		}
	}

	/**
	 * 停止掉音效
	 */
	public void stopSoundPool() {
		if (soundPool != null) {
			soundPool.stop(soundId);
			soundPool.unload(soundId);
			soundPool.release();
			soundPool = null;
		}
	}

	public void playSoundPool(int index, Boolean isLoop) {
		if (soundPool != null) {
			int loopFlag;
			Integer ret = soundPoolMapId.get(index);

			if (null == ret) {
				LogUtil.d("注意：你需要播放的音频文件，没有加在到内存中!!!!");
				return;
			}

			if (isLoop) {
				loopFlag = -1;
			} else {
				loopFlag = 0;
			}

			soundId = ret;
			// 在播放之前取得系统音量
			getStreamVolumeCurrent();
			// 播放声音
			soundPool.play(soundId, streamVolumeCurrent, streamVolumeCurrent, 1, loopFlag, 1f);
		}
	}

	/**
	 * 停止掉全部的游戏中播放的全部音乐
	 */
	public void stopAllSound() {
		stopMediaPlayBgSound();
		stopSoundPool();
	}

	/**
	 * @return the streamVolumeMax
	 */
	public int getStreamVolumeMax() {
		return streamVolumeMax;
	}

	/**
	 * @param streamVolumeMax
	 *            the streamVolumeMax to set
	 */
	public void setStreamVolumeMax(int streamVolumeMax) {
		this.streamVolumeMax = streamVolumeMax;
	}

	/**
	 * @return the streamVolumeCurrent
	 */
	public int getStreamVolumeCurrent() {
		streamVolumeCurrent = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		return streamVolumeCurrent;
	}

	/**
	 * @param streamVolumeCurrent
	 *            the streamVolumeCurrent to set
	 */
	public void setStreamVolumeCurrent(int streamVolumeCurrent) {
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, streamVolumeCurrent, 0);
		this.streamVolumeCurrent = streamVolumeCurrent;
	}

	/**
	 * 设置静音模式
	 * 
	 * @param flag
	 *            flag--true 设置静音模式 flag--false 设置非静音模式
	 */
	public void setSilentMode() {
		isSilent = true;
		audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
	}

	public void setNormalMode() {
		isSilent = false;
		audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
	}

	public boolean isSilent() {
		return isSilent;
	}

	public void changeSoundMode() {
		if (isSilent()) {
			setNormalMode();
		} else {
			setSilentMode();
		}
	}

}
