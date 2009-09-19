package net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
/**Found on http://www.tutorials.de/forum/java/215919-zip-entpacken.html
 * 
 * @author Thomas Darimont
 *
 */
public class ZipArchiveExtractor {


	public static void extract(String source,String dest) throws Exception {
		new ZipArchiveExtractor().extractArchive(new File(source), new File(dest));
	}


	public void extractArchive(File archive, File destDir) throws Exception {
		if (!destDir.exists()) {
			destDir.mkdir();
		}

		ZipFile zipFile = new ZipFile(archive);
		Enumeration entries = zipFile.entries();

		byte[] buffer = new byte[16384];
		int len;
		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();

			String entryFileName = entry.getName();

			File  dir = buildDirectoryHierarchyFor(entryFileName, destDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			if (!entry.isDirectory()) {
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(new File(destDir, entryFileName)));

				BufferedInputStream bis = new BufferedInputStream(zipFile
						.getInputStream(entry));

				while ((len = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, len);
				}

				bos.flush();
				bos.close();
				bis.close();
			}
		}
	}

	private File buildDirectoryHierarchyFor(String entryName, File destDir) {
		int lastIndex = entryName.lastIndexOf('/');
		String internalPathToEntry = entryName.substring(0, lastIndex + 1);
		return new File(destDir, internalPathToEntry);
	}
}