package CCoin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class DigitalSignature {
	private Miner miner;
	private Block block;
	private int prime = 23; // Prime number used to generate public and private keys
	private String hash;
	private int privateKey;
	private int publicKeyX;
	private int publicKeyY;
	private int G1 = 4; // Base point on elliptic curve is G = (G1, G2)
	private int G2 = 5;
	private int n = 40; // Order of G
	private int a = 9; //5; // From elliptic curve equation y^2 = x^3 + ax + b
	private int b = 17; //7;
	
	private int digitalSignatureX;
	private int digitalSignatureY;

	public DigitalSignature(Miner m, Block b, String h) {
		this.miner = m;
		this.block = b;
		this.hash = h;
	}
	
	/**
	 * This method takes a pair of points on the elliptic curve P = (xP, yP) and Q = (xQ, yQ) and returns xC in C = P + Q = (xC, yC).  If yP = 0, null is
	 * returned instead, since that is undefined.
	 */
	public Integer ellipticCurveAddX(int xP, int yP, int xQ, int yQ) {
		
		int lambda;
		if (xP == xQ && yP == yQ && yP != 0) {
			lambda = ((3 * xP * xP + a) / (2 * yP)) % prime;
		} else {
			lambda = ((yQ - yP) / (xQ - xP)) % prime;
		}
		
		return (lambda * lambda - xP - xQ) % prime;
	}
	
	/**
	 * This method takes a pair of points on the elliptic curve P = (xP, yP) and Q = (xQ, yQ) and returns yC in C = P + Q = (xC, yC).  If yP = 0, null is
	 * returned instead, since that is undefined.
	 */
	public Integer ellipticCurveAddY(int xP, int yP, int xQ, int yQ) {
		
		int lambda;
		if (xP == xQ && yP == yQ && yP != 0) {
			lambda = ((3 * xP * xP + a) / (2 * yP)) % prime;
		} else {
			lambda = ((yQ - yP) / (xQ - xP)) % prime;
		}
		
		int xC = (lambda * lambda - xP - xQ) % prime;
		return ( lambda * (xP - xC) - yP) % prime;
	}
	
	/**
	 * This method takes a point P = (xP, yP) and a number n and returns xC in C = nP = P + P + P + ... + P (multiplication is defined as repeated addition, so be
	 * P will be added to itself n times).
	 */
	public Integer ellipticCurveMultiplyX(int xP, int yP, int n) {
		
		int xC = xP;
		int yC = yP;
		
		for (int i = 0; i < n; i++) {
			int newXC = ellipticCurveAddX(xC,yC,xP,yP);
			int newYC = ellipticCurveAddY(xC,yC,xP,yP);
			xC = newXC;
			yC = newYC;
		}
		
		return xC;
	}
	
	/**
	 * This method takes a point P = (xP, yP) and a number n and returns yC in C = nP = P + P + P + ... + P (multiplication is defined as repeated addition, so be
	 * P will be added to itself n times).
	 */
	public Integer ellipticCurveMultiplyY(int xP, int yP, int n) {
		
		int xC = xP;
		int yC = yP;
		
		for (int i = 0; i < n; i++) {
			int newXC = ellipticCurveAddX(xC,yC,xP,yP);
			int newYC = ellipticCurveAddY(xC,yC,xP,yP);
			xC = newXC;
			yC = newYC;
		}
		
		return yC;
	}
	
	/**
	 * This method generates public and private keys for the signer.
	 */
	public void generatePublicPrivateKeys() {
		Random rng = new Random(n-1);
		
		int d = rng.nextInt() + 1; // d is the private key
		
		int xQ = ellipticCurveMultiplyX(G1,G2,d); // The point Q = (xQ,yQ) is the public key
		int yQ = ellipticCurveMultiplyY(G1,G2,d);
		
		this.privateKey = d;
		this.publicKeyX = xQ;
		this.publicKeyY = yQ;
		
	}
	
	/**
	 * This method generates a signature for the miner, block, and hash provided through the constructor.
	 */
	public void generateSignature() {
		Random rng = new Random(n-1);
		int r = 0;
		int k = 1;
		int s = 0;
		
		while (s == 0) {
			while (r == 0) {
				k = rng.nextInt() + 1;
			
				int xP = ellipticCurveMultiplyX(G1,G2,k);
				int yP = ellipticCurveMultiplyY(G1,G2,k);
				
				r = xP % n;
			}
			
			double t = Math.pow(k, -1) % n;
			
			String temp = block + this.hash + this.miner;
			int e = temp.hashCode();
			
			s = (int) Math.pow(k, -1) * (e + this.privateKey * r) % n;
		}
		
		this.digitalSignatureX = r;
		this.digitalSignatureY = s;
	}
	
	public int[] getDigitalSignature() {
		int[] ret = {this.digitalSignatureX, this.digitalSignatureY};
		return ret;
	}
	
	public boolean verifySignature() {
		if (!(this.digitalSignatureX < n && this.digitalSignatureX > 0)
				|| !(this.digitalSignatureY < n && this.digitalSignatureY > 0)) {
			return false;
		}
		
		String temp = this.block + this.hash + this.miner;
		int e = temp.hashCode();
		
		int w = (int) Math.pow(this.digitalSignatureY, -1);
		
		int u1 = e * w;
		
		int u2 = this.digitalSignatureX * w;
		
		int u1GX = ellipticCurveMultiplyX(G1, G2, u1);
		int u1GY = ellipticCurveMultiplyY(G1, G2, u1);
		
		int u2GX = ellipticCurveMultiplyX(G1, G2, u2);
		int u2GY = ellipticCurveMultiplyY(G1, G2, u2);
		
		int XX = ellipticCurveAddX(u1GX, u1GY, u2GX, u2GY);
		int XY = ellipticCurveAddY(u1GX, u1GY, u2GX, u2GY);
		
		if (XX == 0 && XY == 0) {
			return false;
		}
		
		int v = XX % n;
		
		if (v != this.digitalSignatureX) {
			return false;
		}
		
		return true;
	}
	
}
