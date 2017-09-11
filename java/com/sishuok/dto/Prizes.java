package com.sishuok.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.sishuok.entity.PrizeClass;

public class Prizes implements Serializable, Model {
	 private static final long serialVersionUID = 1L;  
	    private int prize0;  
	    private int prize1;  
	    private int prize2;  
	    private int prize3;  
	    private int prize4;  
	    
		public int getPrize0() {
			return prize0;
		}
		public void setPrize0(int prize0) {
			this.prize0 = prize0;
		}
		public int getPrize1() {
			return prize1;
		}
		public void setPrize1(int prize1) {
			this.prize1 = prize1;
		}
		public int getPrize2() {
			return prize2;
		}
		public void setPrize2(int prize2) {
			this.prize2 = prize2;
		}
		public int getPrize3() {
			return prize3;
		}
		public void setPrize3(int prize3) {
			this.prize3 = prize3;
		}
		public int getPrize4() {
			return prize4;
		}
		public void setPrize4(int prize4) {
			this.prize4 = prize4;
		}
		@Override
		public Model addAllAttributes(Collection<?> arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Model addAllAttributes(Map<String, ?> arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Model addAttribute(Object arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Model addAttribute(String arg0, Object arg1) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Map<String, Object> asMap() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public boolean containsAttribute(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public Model mergeAttributes(Map<String, ?> arg0) {
			// TODO Auto-generated method stub
			return null;
		}  
}
