package MyTreePackage;

public class ComparableBinaryTree<T extends Comparable<? super T>>
		extends BinaryTree<T>
		implements ComparableTreeInterface<T>
{
	@Override
	// This method will find the minimum value of in the
	// tree rooted at the given node. It goes through every
	// value in the tree and compares it to the current min
	// object.
	public T getMin()
	{
		return getMin(getRootNode());
	}


	private T getMin(BinaryNode<T> n)
	{
		T min = n.getData();
		if (n.getLeftChild() != null)
		{
			T minL = getMin(n.getLeftChild());
			if (min.compareTo(minL) > 0)
			{
				min = minL;
			}
		}
		if (n.getRightChild() != null)
		{
			T minR = getMin(n.getRightChild());
			if (min.compareTo(minR) > 0)
			{
				min = minR;
			}
		}
		return min;
	}

	@Override
	// This method will find the maximum value of in the
	// tree rooted at the given node. It goes through every
	// value in the tree and compares it to the current max
	// object.
	public T getMax()
	{
		return getMax(getRootNode());
	}

	private T getMax(BinaryNode<T> n)
	{
		T max = n.getData();
		if (n.getLeftChild() != null)
		{
			T maxL = getMax(n.getLeftChild());
			if (max.compareTo(maxL) < 0)
			{
				max = maxL;
			}
		}
		if (n.getRightChild() != null)
		{
			T maxR = getMax(n.getRightChild());
			if (max.compareTo(maxR) < 0)
			{
				max = maxR;
			}
		}
		return max;
	}

	@Override
	public boolean isBST()
	{
		return isBST(getRootNode());
	}

	// This method will make sure that the left side of the subtree rooted at n
	// always has a max that is lower than n and the right side of the subtree
	// always has a min greater than n.
	private boolean isBST(BinaryNode<T> n)
	{
		if (n.getLeftChild() != null)
		{
			T maxL = getMax(n.getLeftChild());
			if (maxL.compareTo(n.getData()) > 0)
			{
				return false;
			}
			isBST(n.getLeftChild());
		}
		if (n.getRightChild() != null)
		{
			T minR = getMin(n.getRightChild());
			if (minR.compareTo(n.getData()) < 0)
			{
				return false;
			}
			isBST(n.getRightChild());
		}

		return true;
	}



}
