import requests
from bs4 import BeautifulSoup

class Downloader:
	"""Handles downloading and parsing web content of the relevant base site and individual page sites"""
	def __init__(self, base_url):
		super(Downloader, self).__init__()
		self.base_url = base_url

	def download_page(self, url, homepage):
		print('Downloading page: ' + url + '...')
		try:
			page = requests.get(url)
		except Exception as e:
			print('Sorry, the download failed. Please check your internet connection.')
			exit(0)
		finally:
			if not page.status_code == 200:
				print('Failed to download base page. Terminating program.')
				exit(0)

		soup = BeautifulSoup(page.content, 'html.parser')
		links = list(soup.find_all('a', href=True))

		for link in links:
			if homepage:
				link = url + '/' + link['href']
				if not str(link).endswith('index.html'):
					# not a play link
					continue
				self.download_page(link, False)
			else:
				link = url.replace('index.html', str(link['href']))
				if link.endswith('full.html') or not link.endswith('.html'):
					continue
				# amazon and back links rejected
				self.parse_scene(link)

	def parse_scene(self, scene):
		page = requests.get(scene)
		scene_soup = BeautifulSoup(page.content, 'html.parser')
		scene_model = list(scene_soup.body.find_all('a'))
		scene_text = ''
		for link in scene_model:
			scene_text = scene_text + '\n' + link.string
		scene_filepath = './data/' + scene[scene.rfind('/') + 1:].replace('.', '') + '.txt'
		scene_file = open(scene_filepath, 'w')
		scene_file.write(scene_text)

base_url = 'http://shakespeare.mit.edu'
downloader = Downloader(base_url)
downloader.download_page(base_url, True)