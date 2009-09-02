/*
 * AndroVoIP -- VoIP for Android.
 *
 * Copyright (C), 2009, Russell Bryant
 *
 * Russell Bryant <russell@russellbryant.net>
 *
 * AndroVoIP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AndroVoIP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AndroVoIP.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.androvoip.audio;

public class ULAW {
	private static final boolean ZEROTRAP = true;
	private static final short BIAS = 0x84;
	private static final int CLIP = 32635;
	private static final int exp_lut1[] = { 0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3,
			3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7 };
	private static final short[] u2l = { -32124, -31100, -30076, -29052,
			-28028, -27004, -25980, -24956, -23932, -22908, -21884, -20860,
			-19836, -18812, -17788, -16764, -15996, -15484, -14972, -14460,
			-13948, -13436, -12924, -12412, -11900, -11388, -10876, -10364,
			-9852, -9340, -8828, -8316, -7932, -7676, -7420, -7164, -6908,
			-6652, -6396, -6140, -5884, -5628, -5372, -5116, -4860, -4604,
			-4348, -4092, -3900, -3772, -3644, -3516, -3388, -3260, -3132,
			-3004, -2876, -2748, -2620, -2492, -2364, -2236, -2108, -1980,
			-1884, -1820, -1756, -1692, -1628, -1564, -1500, -1436, -1372,
			-1308, -1244, -1180, -1116, -1052, -988, -924, -876, -844, -812,
			-780, -748, -716, -684, -652, -620, -588, -556, -524, -492, -460,
			-428, -396, -372, -356, -340, -324, -308, -292, -276, -260, -244,
			-228, -212, -196, -180, -164, -148, -132, -120, -112, -104, -96,
			-88, -80, -72, -64, -56, -48, -40, -32, -24, -16, -8, 0, 32124,
			31100, 30076, 29052, 28028, 27004, 25980, 24956, 23932, 22908,
			21884, 20860, 19836, 18812, 17788, 16764, 15996, 15484, 14972,
			14460, 13948, 13436, 12924, 12412, 11900, 11388, 10876, 10364,
			9852, 9340, 8828, 8316, 7932, 7676, 7420, 7164, 6908, 6652, 6396,
			6140, 5884, 5628, 5372, 5116, 4860, 4604, 4348, 4092, 3900, 3772,
			3644, 3516, 3388, 3260, 3132, 3004, 2876, 2748, 2620, 2492, 2364,
			2236, 2108, 1980, 1884, 1820, 1756, 1692, 1628, 1564, 1500, 1436,
			1372, 1308, 1244, 1180, 1116, 1052, 988, 924, 876, 844, 812, 780,
			748, 716, 684, 652, 620, 588, 556, 524, 492, 460, 428, 396, 372,
			356, 340, 324, 308, 292, 276, 260, 244, 228, 212, 196, 180, 164,
			148, 132, 120, 112, 104, 96, 88, 80, 72, 64, 56, 48, 40, 32, 24,
			16, 8, 0 };

	public static short ulaw2linear(byte ulawbyte) {
		return u2l[ulawbyte & 0xFF];
	}

	/**
	 * Converts a linear signed 16bit sample to a uLaw byte. Ported to Java by
	 * fb. <br>
	 * Originally by:<br>
	 * Craig Reese: IDA/Supercomputing Research Center <br>
	 * Joe Campbell: Department of Defense <br>
	 * 29 September 1989 <br>
	 */
	public static byte linear2ulaw(int sample) {
		int sign, exponent, mantissa, ulawbyte;

		if (sample > 32767) {
			sample = 32767;
		} else if (sample < -32768) {
			sample = -32768;
			/* Get the sample into sign-magnitude. */
		}
		sign = (sample >> 8) & 0x80; /* set aside the sign */
		if (sign != 0) {
			sample = -sample; /* get magnitude */
		}
		if (sample > CLIP) {
			sample = CLIP; /* clip the magnitude */

			/* Convert from 16 bit linear to ulaw. */
		}
		sample = sample + BIAS;
		exponent = exp_lut1[(sample >> 7) & 0xFF];
		mantissa = (sample >> (exponent + 3)) & 0x0F;
		ulawbyte = ~(sign | (exponent << 4) | mantissa);
		if (ZEROTRAP) {
			if (ulawbyte == 0) {
				ulawbyte = 0x02; /* optional CCITT trap */
			}
		}
		return ((byte) ulawbyte);
	}
};
