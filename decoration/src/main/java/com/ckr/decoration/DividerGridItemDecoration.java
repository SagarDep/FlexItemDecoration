package com.ckr.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * @author zhy
 */
public class DividerGridItemDecoration extends BaseItemDecoration {
	private static final String TAG = "GridItemDecoration";
	protected int mSpanCount = 1;

	public DividerGridItemDecoration(Context context, int mSpanCount) {
		super(context, GRID, VERTICAL);
		this.mSpanCount = mSpanCount;
	}

	public DividerGridItemDecoration(Context context, int orientation, int mSpanCount) {
		super(context, GRID, orientation);
		this.mSpanCount = mSpanCount;
	}

	public DividerGridItemDecoration(Context context, int orientation, int mSpanCount, @DrawableRes int drawableId) {
		super(context, GRID, orientation, drawableId);
		this.mSpanCount = mSpanCount;
	}

	private DividerGridItemDecoration(Builder builder) {
		super(builder);
		this.mSpanCount = builder.mSpanCount;
	}

	public DividerGridItemDecoration setShowOtherStyle(boolean showOtherStyle) {
		isShowOtherStyle = showOtherStyle;
		return this;
	}

	public void showOtherStyle() {
		isShowOtherStyle = true;
	}

	//绘制水平分割线
	@Override
	protected void drawHorizontal(Canvas c, RecyclerView parent) {
		int childCount = parent.getChildCount();//可视item的个数
		int itemCount = parent.getAdapter().getItemCount();//item个数
		Log.d(TAG, "drawHorizontal: childCount:" + childCount + ",itemCount:" + itemCount);
		Log.d(TAG, "drawHorizontal: width:" + mDividerWidth + ",height:" + mDividerHeight);
		int left = 0;
		int top = 0;
		int right = 0;
		int bottom = 0;
		boolean headerPosHandle = true;
		boolean footerPosHandle = true;
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
					.getLayoutParams();
			int topDividerHeight = mDividerHeight;
			int bottomDividerHeight = mDividerHeight;
			if (mOrientation == VERTICAL) {
				int leftDividerWidth = mDividerWidth;
				int rightDividerWidth = mDividerWidth;
				if (noDrawLeftDivider) {
					if (i % mSpanCount == 0) {
						leftDividerWidth = 0;
					}
				}
				if (noDrawRightDivider) {
					if (i % mSpanCount == mSpanCount - 1) {
						rightDividerWidth = 0;
					}
				}
				left = child.getLeft() - params.leftMargin - leftDividerWidth;//计算分割线的左边
				right = child.getRight() + params.rightMargin + rightDividerWidth;//计算分割线的右边
				if (noDrawHeaderDivider) {//顶部分割线处理
					if (headerPosHandle) {
						int adapterPosition = parent.getChildAdapterPosition(child);
						if (mSpanCount > adapterPosition) {
							Log.d(TAG, "drawHorizontal: noDrawHeaderDivider:" + i + ",adapterPosition:" + adapterPosition);
							topDividerHeight = 0;
							if (adapterPosition == mSpanCount - 1) {
								headerPosHandle = false;
							}
						} else {
							headerPosHandle = false;
						}
					}
				} else {
					/*if (isRedrawHeaderDivider) {
					if (mOrientation == VERTICAL) {
						int adapterPosition = parent.getChildAdapterPosition(child);
						if (mSpanCount > adapterPosition) {
							topDividerHeight = mRedrawDividerHeight;
							if (mHeaderDividerDrawable != null) {
								//---------item的上方的分割线绘制
								bottom = child.getTop() - params.topMargin;//计算分割线的下边
								top = bottom - topDividerHeight;//计算分割线的上边
								mHeaderDividerDrawable.setBounds(left, top, right, bottom);
								mHeaderDividerDrawable.draw(c);
								//---------item的下方的分割线绘制---------
								Log.d(TAG, "drawHorizontal: getBottom:" + child.getBottom());
								top = child.getBottom() + params.bottomMargin;//计算分割线的上边
								bottom = top + bottomDividerHeight;//计算分割线的下边
								Log.d(TAG, "drawHorizontal: bottom22:" + bottom);
								mDivider.setBounds(left, top, right, bottom);
								mDivider.draw(c);
							}
						} else {
							headerPosHandle = false;
						}
					}
				}*/
				}
				if (noDrawFooterDivider) {//底部分割线处理
					if (footerPosHandle) {
						int rowNum = itemCount % mSpanCount == 0 ? itemCount / mSpanCount - 1 : itemCount / mSpanCount;
						int startNum = childCount - (itemCount - rowNum * mSpanCount);
						if (startNum <= i) {
							int adapterPosition = parent.getChildAdapterPosition(child);
							if (rowNum * mSpanCount <= adapterPosition) {
								Log.d(TAG, "drawHorizontal: noDrawFooterDivider:" + i + ",adapterPosition:" + adapterPosition);
								bottomDividerHeight = 0;
								if (adapterPosition == itemCount - 1) {
									footerPosHandle = false;
								}
							} else {
								footerPosHandle = false;
							}
						}
					}
				}
				//---------item的上方的分割线绘制---------
				Log.d(TAG, "drawHorizontal: getTop:" + child.getTop() + ",i:" + i + ",topDividerHeight:" + topDividerHeight + ",bottomDividerHeight:" + bottomDividerHeight);
				bottom = child.getTop() - params.topMargin;//计算分割线的下边
				Log.d(TAG, "drawHorizontal: bottom:" + bottom);
				top = bottom - topDividerHeight;//计算分割线的上边
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(c);
				//------------------end-------------------
				//---------item的下方的分割线绘制---------
				Log.d(TAG, "drawHorizontal: getBottom:" + child.getBottom());
				top = child.getBottom() + params.bottomMargin;//计算分割线的上边
				bottom = top + bottomDividerHeight;//计算分割线的下边
				Log.d(TAG, "drawHorizontal: bottom22:" + bottom);
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(c);
				//------------------end-------------------
			} else {
				left = child.getLeft() - params.leftMargin;//计算分割线的左边
				right = child.getRight() + params.rightMargin;//计算分割线的右边
				if (noDrawHeaderDivider) {//顶部分割线处理
					if (i % mSpanCount == 0) {
						Log.e(TAG, "drawHorizontal: noDrawHeaderDivider:" + i);
						topDividerHeight = 0;
					}
				}
				if (noDrawFooterDivider) {//底部分割线处理
					if (i % mSpanCount == mSpanCount - 1) {
						Log.e(TAG, "drawHorizontal: noDrawFooterDivider:" + i);
						bottomDividerHeight = 0;
					}
				}
				//---------item的上方的分割线绘制---------
				Log.d(TAG, "drawHorizontal: getTop:" + child.getTop() + ",i:" + i + ",topDividerHeight:" + topDividerHeight + ",bottomDividerHeight:" + bottomDividerHeight);
				bottom = child.getTop() - params.topMargin;//计算分割线的下边
				Log.d(TAG, "drawHorizontal: bottom:" + bottom);
				top = bottom - topDividerHeight;//计算分割线的上边
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(c);
				//------------------end-------------------
				//---------item的下方的分割线绘制---------
				Log.d(TAG, "drawHorizontal: getBottom:" + child.getBottom());
				top = child.getBottom() + params.bottomMargin;//计算分割线的上边
				bottom = top + bottomDividerHeight;//计算分割线的下边
				Log.d(TAG, "drawHorizontal: bottom22:" + bottom);
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(c);
				//------------------end-------------------
			}
		}
	}

	//绘制竖直分割线
	@Override
	protected void drawVertical(Canvas c, RecyclerView parent) {
		final int childCount = parent.getChildCount();//可视item的个数
		int itemCount = parent.getAdapter().getItemCount();//item个数
		int left = 0;
		int top = 0;
		int right = 0;
		int bottom = 0;
		boolean leftPosHandle = true;
		boolean rightPosHandle = true;
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
					.getLayoutParams();
			int leftDividerWidth = mDividerWidth;
			int rightDividerWidth = mDividerWidth;
			if (mOrientation == VERTICAL) {
				top = child.getTop() - params.topMargin;
				bottom = child.getBottom() + params.bottomMargin;
				if (noDrawLeftDivider) {
					if (i % mSpanCount == 0) {
						leftDividerWidth = 0;
					}
				}
				if (noDrawRightDivider) {
					if (i % mSpanCount == mSpanCount - 1) {
						rightDividerWidth = 0;
					}
				}
				//---------item的左边的分割线绘制---------
				Log.d(TAG, "drawVertical: getLeft:" + child.getLeft() + ",i:" + i + ",leftDividerWidth:" + leftDividerWidth + ",rightDividerWidth:" + rightDividerWidth);
				right = child.getLeft() - params.leftMargin;
				left = right - leftDividerWidth;
				Log.d(TAG, "drawVertical: right:" + right + ",left:" + left);
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(c);
				//------------------end-------------------
				//---------item的右边的分割线绘制---------
				Log.d(TAG, "drawVertical: getRight:" + child.getRight());
				left = child.getRight() + params.rightMargin;
				right = left + rightDividerWidth;
				Log.d(TAG, "drawVertical: right222:" + right + ",left222:" + left);
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(c);
				//------------------end-------------------
			} else {
				int topDividerWidth = mDividerHeight;
				int bottomDividerWidth = mDividerHeight;
				if (noDrawHeaderDivider) {
					if (i % mSpanCount == 0) {
						topDividerWidth = 0;
					}
				}
				if (noDrawFooterDivider) {
					if (i % mSpanCount == mSpanCount - 1) {
						bottomDividerWidth = 0;
					}
				}
				top = child.getTop() - params.topMargin - topDividerWidth;
				bottom = child.getBottom() + params.bottomMargin + bottomDividerWidth;
				if (noDrawLeftDivider) {
					if (leftPosHandle) {
						int adapterPosition = parent.getChildAdapterPosition(child);
						if (mSpanCount > adapterPosition) {
							Log.e(TAG, "drawVertical: noDrawHeaderDivider:" + i + ",adapterPosition:" + adapterPosition);
							leftDividerWidth = 0;
							if (adapterPosition == mSpanCount - 1) {
								leftPosHandle = false;
							}
						} else {
							leftPosHandle = false;
							leftDividerWidth = mDividerWidth;
						}
					} else {
						leftDividerWidth = mDividerWidth;
					}
				}
				if (noDrawRightDivider) {
					if (rightPosHandle) {
						int columnNum = itemCount % mSpanCount == 0 ? itemCount / mSpanCount - 1 : itemCount / mSpanCount;
						int startNum = childCount - (itemCount - columnNum * mSpanCount);
						if (startNum <= i) {
							int adapterPosition = parent.getChildAdapterPosition(child);
							if (columnNum * mSpanCount <= adapterPosition) {
								Log.d(TAG, "drawVertical: noDrawFooterDivider:" + i + ",adapterPosition:" + adapterPosition);
								rightDividerWidth = 0;
								if (adapterPosition == itemCount - 1) {
									rightPosHandle = false;
								}
							} else {
								rightPosHandle = false;
							}
						}
					}
				}
				//---------item的左边的分割线绘制---------
				Log.d(TAG, "drawVertical: getLeft:" + child.getLeft() + ",i:" + i + ",leftDividerWidth:" + leftDividerWidth + ",rightDividerWidth:" + rightDividerWidth);
				right = child.getLeft() - params.leftMargin;
				left = right - leftDividerWidth;
				Log.d(TAG, "drawVertical: right:" + right + ",left:" + left);
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(c);
				//------------------end-------------------
				//---------item的右边的分割线绘制---------
				Log.d(TAG, "drawVertical: getRight:" + child.getRight());
				left = child.getRight() + params.rightMargin;
				right = left + rightDividerWidth;
				Log.d(TAG, "drawVertical: right222:" + right + ",left222:" + left);
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(c);
				//------------------end-------------------
			}
		}
	}

	/**
	 * 要想清楚outRect作用，请看{@link android.support.v7.widget.GridLayoutManager}源码，如：measureChild().
	 */
	@Override
	public void getItemOffsets(Rect outRect, int itemPosition,
							   RecyclerView parent) {
		Log.e(TAG, "getItemOffsets: pos:" + itemPosition);
		int left = mDividerWidth;
		int top = mDividerHeight;
		int right = mDividerWidth;
		int bottom = mDividerHeight;
		if (mOrientation == VERTICAL) {
			if (noDrawHeaderDivider) {
				if (mSpanCount > itemPosition) {
					Log.d(TAG, "getItemOffsets: noDrawHeaderDivider:" + itemPosition);
					top = 0;
				}
			} else {
				if (isRedrawHeaderDivider) {
					if (mSpanCount > itemPosition) {
						top = mHeaderDividerHeight;
					}
				}
			}
			if (noDrawFooterDivider) {
				int itemCount = parent.getAdapter().getItemCount();
				int rowNum = itemCount % mSpanCount == 0 ? itemCount / mSpanCount - 1 : itemCount / mSpanCount;
				if (rowNum * mSpanCount <= itemPosition) {
					Log.d(TAG, "getItemOffsets: noDrawFooterDivider:" + itemPosition);
					bottom = 0;
				}
			} else {
				if (isRedrawFooterDivider) {
					int itemCount = parent.getAdapter().getItemCount();
					int rowNum = itemCount % mSpanCount == 0 ? itemCount / mSpanCount - 1 : itemCount / mSpanCount;
					if (rowNum * mSpanCount <= itemPosition) {
						bottom = mFooterDividerHeight;
					}
				}
			}
			if (noDrawLeftDivider) {
				if (itemPosition % mSpanCount == 0) {
					left = 0;
				}
			} else {

			}
			if (noDrawRightDivider) {
				if (itemPosition % mSpanCount == mSpanCount - 1) {
					right = 0;
				}
			}
			if (isSubDivider) {
				int itemCount = parent.getAdapter().getItemCount();
				int rowCount = itemCount % mSpanCount == 0 ? itemCount / mSpanCount : itemCount / mSpanCount + 1;
				if (mStartIndex >= rowCount - 1) {
					isSubDivider = false;
				} else {
					int rowNum = (itemPosition + 1) % mSpanCount == 0 ? (itemPosition + 1) / mSpanCount : (itemPosition + 1) / mSpanCount + 1;
					if (rowNum >= mStartIndex && rowNum < Math.min(mEndIndex, rowCount)) {
						Log.e(TAG, "getItemOffsets: mStartIndex:" + mStartIndex + ",mEndIndex:" + mEndIndex + ",itemPosition:" + itemPosition);
						if ((rowNum - mStartIndex) % mSpanCount == 0) {
							bottom = mSubDividerHeight;
						} else {
							top = mSubDividerHeight;
						}
					}
				}
			}
			if (isRedrawDivider) {
				int itemCount = parent.getAdapter().getItemCount();
				int rowCount = itemCount % mSpanCount == 0 ? itemCount / mSpanCount : itemCount / mSpanCount + 1;
				if (mDividerIndex >= rowCount - 1) {
					isRedrawDivider = false;
				}
				int rowNum = (itemPosition + 1) % mSpanCount == 0 ? (itemPosition + 1) / mSpanCount : (itemPosition + 1) / mSpanCount + 1;
				if ((rowNum - 1) == mDividerIndex) {
					Log.e(TAG, "getItemOffsets: mStartIndex:" + mStartIndex + ",mEndIndex:" + mEndIndex + ",itemPosition:" + itemPosition);
					bottom = mRedrawDividerHeight;
				} else if (mDividerIndex + 1 == rowCount) {
					top = mRedrawDividerHeight;
				}
			}

		} else {
			if (noDrawHeaderDivider) {
				if (itemPosition % mSpanCount == 0) {
					Log.d(TAG, "getItemOffsets: noDrawHeaderDivider:" + itemPosition);
					top = 0;
				}
			}
			if (noDrawFooterDivider) {
				if (itemPosition % mSpanCount == mSpanCount - 1) {
					Log.e(TAG, "getItemOffsets: noDrawFooterDivider:" + itemPosition);
					bottom = 0;
				}
			}
			if (noDrawLeftDivider) {
				if (mSpanCount > itemPosition) {
					Log.e(TAG, "getItemOffsets: noDrawLeftDivider:" + itemPosition);
					left = 0;
				}
			}
			if (noDrawRightDivider) {
				int itemCount = parent.getAdapter().getItemCount();
				int columnCount = itemCount % mSpanCount == 0 ? itemCount / mSpanCount - 1 : itemCount / mSpanCount;
				if (columnCount * mSpanCount <= itemPosition) {
					right = 0;
				}
			}
		}

        /*
		* left：代表item的左边分割线占有的x轴长度
        * top：代表item的顶部分割线占有的y轴长度
        * right：代表item的右边分割线占有的x轴长度
        * bottom：代表item的底部分割线占有的y轴长度
        * */
		if (isShowOtherStyle) {
			outRect.set(0, 0, right, bottom);
		} else {
			outRect.set(left, top, right, bottom);
		}
	}

	public static class Builder extends BaseBuilder {
		private int mSpanCount = 1;

		public Builder(Context context, int mSpanCount) {
			super(context, GRID);
			this.mSpanCount = mSpanCount;
			if (this.mSpanCount == 1) {
				noDrawLeftDivider = true;
				noDrawRightDivider = true;
			}
		}

		public Builder(Context context, int mOrientation, int mSpanCount) {
			super(context, GRID, mOrientation);
			this.mSpanCount = mSpanCount;
			if (this.mSpanCount == 1) {
				noDrawLeftDivider = true;
				noDrawRightDivider = true;
			}
		}

		/**
		 * @param startIndex 当mOrientation==Vertical时，startIndex代表起始行的下标；否则，startIndex代表起始列的下标
		 * @param endIndex   当mOrientation==Vertical时，endIndex代表末尾行的下标；否则，endIndex代表末尾列的下标
		 * @return
		 */
		@Override
		public BaseBuilder subDivider(@IntRange(from = 0) int startIndex, @IntRange(from = 1) int endIndex) {
			return super.subDivider(startIndex, endIndex);
		}

		public DividerGridItemDecoration build() {
			return new DividerGridItemDecoration(this);
		}
	}

}
