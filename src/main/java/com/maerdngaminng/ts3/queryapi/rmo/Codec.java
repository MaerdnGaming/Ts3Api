package com.maerdngaminng.ts3.queryapi.rmo;

import lombok.Getter;

public enum Codec {
	CODEC_SPEEX_NARROWBAND(0), CODEC_SPEEX_WIDEBAND(1), CODEC_SPEEX_ULTRAWIDEBAND(2), CODEC_CELT_MONO(3), CODEC_OPUS_VOICE(4), CODEC_OPUS_MUSIC(5);

	@Getter
	private final int id;
	
	private Codec(int id) {
		this.id = id;
	}
	
	public static Codec getCodecById(int id) {
		switch (id) {
			case 0: return CODEC_SPEEX_NARROWBAND;
			case 1: return CODEC_SPEEX_WIDEBAND;
			case 2: return CODEC_SPEEX_ULTRAWIDEBAND;
			case 3: return CODEC_CELT_MONO;
			case 4: return CODEC_OPUS_VOICE;
			case 5: return CODEC_OPUS_MUSIC;
		}
		System.out.println(id);
		return null;
	}
}
