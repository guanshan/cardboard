package com.wormhole.vrtoolkit.cardboard.proto;

import java.io.IOException;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;

public abstract interface Phone {
	public static final class PhoneParams extends MessageNano implements
			Cloneable {
		private static volatile PhoneParams[] _emptyArray;
		private int bitField0_;
		private float xPpi_;
		private float yPpi_;
		private float bottomBezelHeight_;
		public float[] dEPRECATEDGyroBias;

		public static PhoneParams[] emptyArray() {
			if (_emptyArray == null) {
				synchronized (InternalNano.LAZY_INIT_LOCK) {
					if (_emptyArray == null) {
						_emptyArray = new PhoneParams[0];
					}
				}
			}
			return _emptyArray;
		}

		public float getXPpi() {
			return this.xPpi_;
		}

		public PhoneParams setXPpi(float value) {
			this.xPpi_ = value;
			this.bitField0_ |= 1;
			return this;
		}

		public boolean hasXPpi() {
			return (this.bitField0_ & 0x1) != 0;
		}

		public PhoneParams clearXPpi() {
			this.xPpi_ = 0.0F;
			this.bitField0_ &= -2;
			return this;
		}

		public float getYPpi() {
			return this.yPpi_;
		}

		public PhoneParams setYPpi(float value) {
			this.yPpi_ = value;
			this.bitField0_ |= 2;
			return this;
		}

		public boolean hasYPpi() {
			return (this.bitField0_ & 0x2) != 0;
		}

		public PhoneParams clearYPpi() {
			this.yPpi_ = 0.0F;
			this.bitField0_ &= -3;
			return this;
		}

		public float getBottomBezelHeight() {
			return this.bottomBezelHeight_;
		}

		public PhoneParams setBottomBezelHeight(float value) {
			this.bottomBezelHeight_ = value;
			this.bitField0_ |= 4;
			return this;
		}

		public boolean hasBottomBezelHeight() {
			return (this.bitField0_ & 0x4) != 0;
		}

		public PhoneParams clearBottomBezelHeight() {
			this.bottomBezelHeight_ = 0.0F;
			this.bitField0_ &= -5;
			return this;
		}

		public PhoneParams() {
			clear();
		}

		public PhoneParams clear() {
			this.bitField0_ = 0;
			this.xPpi_ = 0.0F;
			this.yPpi_ = 0.0F;
			this.bottomBezelHeight_ = 0.0F;
			this.dEPRECATEDGyroBias = WireFormatNano.EMPTY_FLOAT_ARRAY;
			this.cachedSize = -1;
			return this;
		}

		public PhoneParams clone() {
			PhoneParams cloned;
			try {
				cloned = (PhoneParams) super.clone();
			} catch (CloneNotSupportedException e) {
				
				throw new AssertionError(e);
			}
			if ((this.dEPRECATEDGyroBias != null)
					&& (this.dEPRECATEDGyroBias.length > 0)) {
				cloned.dEPRECATEDGyroBias = ((float[]) this.dEPRECATEDGyroBias
						.clone());
			}
			return cloned;
		}

		public void writeTo(CodedOutputByteBufferNano output)
				throws IOException {
			if ((this.bitField0_ & 0x1) != 0) {
				output.writeFloat(1, this.xPpi_);
			}
			if ((this.bitField0_ & 0x2) != 0) {
				output.writeFloat(2, this.yPpi_);
			}
			if ((this.bitField0_ & 0x4) != 0) {
				output.writeFloat(3, this.bottomBezelHeight_);
			}
			if ((this.dEPRECATEDGyroBias != null)
					&& (this.dEPRECATEDGyroBias.length > 0)) {
				int dataSize = 4 * this.dEPRECATEDGyroBias.length;
				output.writeRawVarint32(34);
				output.writeRawVarint32(dataSize);
				for (int i = 0; i < this.dEPRECATEDGyroBias.length; i++) {
					output.writeFloatNoTag(this.dEPRECATEDGyroBias[i]);
				}
			}
			super.writeTo(output);
		}

		protected int computeSerializedSize() {
			int size = super.computeSerializedSize();
			if ((this.bitField0_ & 0x1) != 0) {
				size = size
						+ CodedOutputByteBufferNano.computeFloatSize(1,
								this.xPpi_);
			}

			if ((this.bitField0_ & 0x2) != 0) {
				size = size
						+ CodedOutputByteBufferNano.computeFloatSize(2,
								this.yPpi_);
			}

			if ((this.bitField0_ & 0x4) != 0) {
				size = size
						+ CodedOutputByteBufferNano.computeFloatSize(3,
								this.bottomBezelHeight_);
			}

			if ((this.dEPRECATEDGyroBias != null)
					&& (this.dEPRECATEDGyroBias.length > 0)) {
				int dataSize = 4 * this.dEPRECATEDGyroBias.length;
				size += dataSize;
				size++;

				size = size
						+ CodedOutputByteBufferNano
								.computeRawVarint32Size(dataSize);
			}

			return size;
		}

		public PhoneParams mergeFrom(CodedInputByteBufferNano input)
				throws IOException {
			int arrayLength;
			int i;
			float[] newArray;
			while (true) {
				int tag = input.readTag();
				switch (tag) {
				case 0:
					return this;
				default:
					if (!WireFormatNano.parseUnknownField(input, tag)) {
						return this;
					}

					break;
				case 13:
					this.xPpi_ = input.readFloat();
					this.bitField0_ |= 1;
					break;
				case 21:
					this.yPpi_ = input.readFloat();
					this.bitField0_ |= 2;
					break;
				case 29:
					this.bottomBezelHeight_ = input.readFloat();
					this.bitField0_ |= 4;
					break;
				case 37:
					arrayLength = WireFormatNano
							.getRepeatedFieldArrayLength(input, 37);

					i = this.dEPRECATEDGyroBias == null ? 0
							: this.dEPRECATEDGyroBias.length;
					newArray = new float[i + arrayLength];
					if (i != 0) {
						System.arraycopy(this.dEPRECATEDGyroBias, 0, newArray,
								0, i);
					}
					for (; i < newArray.length - 1; i++) {
						newArray[i] = input.readFloat();
						input.readTag();
					}

					newArray[i] = input.readFloat();
					this.dEPRECATEDGyroBias = newArray;
					break;
				case 34:
					int length = input.readRawVarint32();
					int limit = input.pushLimit(length);
					arrayLength = length / 4;
					i = this.dEPRECATEDGyroBias == null ? 0
							: this.dEPRECATEDGyroBias.length;
					newArray = new float[i + arrayLength];
					if (i != 0) {
						System.arraycopy(this.dEPRECATEDGyroBias, 0, newArray,
								0, i);
					}
					for (; i < newArray.length; i++) {
						newArray[i] = input.readFloat();
					}
					this.dEPRECATEDGyroBias = newArray;
					input.popLimit(limit);
				}
			}
		}

		public static PhoneParams parseFrom(byte[] data)
				throws InvalidProtocolBufferNanoException {
			return (PhoneParams) MessageNano.mergeFrom(new PhoneParams(), data);
		}

		public static PhoneParams parseFrom(CodedInputByteBufferNano input)
				throws IOException {
			return new PhoneParams().mergeFrom(input);
		}
	}
}