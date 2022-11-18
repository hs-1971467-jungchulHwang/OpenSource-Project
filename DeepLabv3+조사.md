# DeepLab v3+

1. DeepLab v3+란?
   - Semantic Segmentation을 이용한 이미지 인식
   - 이미지내에 있는 객체들을 의미있는 단위로 분할하는 작업
   - ![image-20221117143624525](/Users/junwoo/Library/Application Support/typora-user-images/image-20221117143624525.png)
   - Yolov5에서는 물체를 빠르게 인식할 수 있고 DeepLab v3+에서는 픽셀단위로 분석하여 조금 더 정교한 분석이 가능
   - 분할한 이후 DeepFashion2 dataset과 접목하여서 의상 분류를 세분화해서 할 수 있게 만들기 위해 사용한 오픈소스

2. DeepLab v3+ 사용방법

   - 학습을 위한 데이터셋 준비

   -  ``torch.utils.data.Dataset`` 이 부분에 데이터셋을 추가하며  [VOC Dataset](https://github.com/VainF/DeepLabV3Plus-Pytorch/blob/bfe01d5fca5b6bb648e162d522eed1a9a8b324cb/datasets/voc.py#L156)과 같은 형태로 데이터셋을 추가

     ```python
     class MyDataset(data.Dataset):
         ...
         @classmethod
         def decode_target(cls, mask):
             """decode semantic mask to RGB image"""
             return cls.cmap[mask]
     ```

   - 이후 학습을 진행

   - Prediction

     - Single image:

     - ```bash
       python predict.py --input datasets/data/cityscapes/leftImg8bit/train/bremen/bremen_000000_000019_leftImg8bit.png  --dataset cityscapes --model deeplabv3plus_mobilenet --ckpt checkpoints/best_deeplabv3plus_mobilenet_cityscapes_os16.pth --save_val_results_to test_results
       ```

     - Image folder:

     - ```bash
       python predict.py --input datasets/data/cityscapes/leftImg8bit/train/bremen  --dataset cityscapes --model deeplabv3plus_mobilenet --ckpt checkpoints/best_deeplabv3plus_mobilenet_cityscapes_os16.pth --save_val_results_to test_results
       ```

   - 결과 시각화

     ~~~python
     ```python
     outputs = model(images)
     preds = outputs.max(1)[1].detach().cpu().numpy()
     colorized_preds = val_dst.decode_target(preds).astype('uint8') # To RGB images, (N, H, W, 3), ranged 0~255, numpy array
     # Do whatever you like here with the colorized segmentation maps
     colorized_preds = Image.fromarray(colorized_preds[0]) # to PIL Image
     ```
     ~~~

     - 결과 이미지
     - ![image-20221117152111592](/Users/junwoo/Library/Application Support/typora-user-images/image-20221117152111592.png)

### 라이센스

MIT License

